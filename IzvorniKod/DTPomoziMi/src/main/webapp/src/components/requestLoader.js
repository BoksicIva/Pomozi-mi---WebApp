import React, { useState } from 'react';
import { Card } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "./style/page.module.css";
import { Formik } from "formik";
import Sidebar from "./sidebar"
import * as Yup from "yup";
import style from "./style/page.module.css";
import {
    GoogleMap,
    useLoadScript,
    Marker,
} from "@react-google-maps/api";
import Geocode from "react-geocode";
import Service from '../service/login-service';
import Datetime from 'react-datetime';
import "react-datetime/css/react-datetime.css";
import moment from 'moment'
import 'moment/locale/hr';


Geocode.setApiKey(process.env.REACT_APP_GOOGLE_MAPS_API_KEY);

const libraries = ["places"];
const mapContainerStyle = {
    height: "100vh",
    width: "60vw",
};

const options = {
    disableDefaultUI: true,
    zoomControl: true,
};

const isValidDate = (current) => {
    return current.isAfter(moment().subtract(1, 'day'));
}
const onFieldChange = (value, form) => {
    let dateTimeValue = value;

    if (value instanceof moment) {
        dateTimeValue = moment(value).format("YYYY-MM-DD HH:mm:ss");
    }

    form.setFieldValue("datetime", dateTimeValue);
}
const onFieldBlur = (form) => {
    form.setFieldTouched("datetime", true);
}

const checkAdress = (values) => {
    return (
        (values.town === null || values.town === "") &&
        (values.country === null || values.country === "") &&
        (values.address === null || values.address === "")
    );
}

const fieldSetter = (value) => {
    if (value === null)
        return "";
    return value;
}


export const Dash = props => {
    const { isLoaded, loadError } = useLoadScript({
        googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
        libraries,
    });
    const [long, setLong] = useState("");
    const [lat, setLat] = useState("");
    const [checked, setchecked] = useState(false);
    const [address, setAddress] = useState(false);

    if (loadError) return "Error";
    if (!isLoaded) return "Loading...";

    document.body.style = 'background-image: none;';

    const handleClick = () => {
        console.log(checked);
        if (checked === false) {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition, showError);
            } else {
                alert("Geolocation is not supported by this browser.");
            }
            setchecked(true);
        } else {
            setchecked(false);
            setLat("");
            setLong("");
        }
    }

    const showPosition = (position) => {
        console.log(position);

        setLong(position.coords.longitude);
        setLat(position.coords.latitude);

        Geocode.fromLatLng(position.coords.latitude, position.coords.longitude).then(
            response => {
                const add = response.results[0].formatted_address;
                console.log(add);
                setAddress(add);
            },
            error => {
                console.error(error);
            }
        );

    }

    const showError = (error) => {
        switch (error.code) {
            case error.PERMISSION_DENIED:
                alert("User denied the request for Geolocation.")
                break;
            case error.POSITION_UNAVAILABLE:
                alert("Location information is unavailable.")
                break;
            case error.TIMEOUT:
                alert("The request to get user location timed out.")
                break;
            case error.UNKNOWN_ERROR:
                alert("An unknown error occurred.")
                break;
        }
    }





    return (
        <>
            <Sidebar />
            <div className={style.background}>
                <div className={style.empthy1}>            </div>
                <div className={style.container}>
                    <Card className="crd col-lg-10 mx-auto">
                        <Formik
                            initialValues={{
                                address: "",
                                phone: "",
                                datetime: "",
                                req: "",
                                town: "",
                                country: ""
                            }}
                            onSubmit={async (values) => {
                                var call = new Object();
                                var location = new Object();
                                if (checked) {
                                    if (address != null) {
                                        var addParts = address.split(",");

                                        if (addParts.length == 4) {
                                            location.adress = addParts[0];
                                            location.state = addParts[3].trim();
                                            location.town = addParts[1].trim() + ", " + addParts[2].trim();
                                        } else if (addParts.length == 3) {
                                            location.adress = addParts[0];
                                            location.state = addParts[2].trim();
                                            location.town = addParts[1].trim();
                                        } else if (addParts.length == 2) {
                                            location.town = addParts[0].trim();
                                            location.country = addParts[1].trim();
                                        } else if (addParts.length == 1)
                                            location.country = addParts[0].trim();
                                        else {
                                            alert("Nije moguće postaviti danu lokaciju");
                                        }

                                        location.longitude = long;
                                        location.latitude = lat;
                                    } else
                                        alert("Nije moguće postaviti danu lokaciju");
                                } else {

                                    if (checkAdress(values))
                                        location = null;
                                    else {

                                        await Geocode.fromAddress(values.country + " " + values.town + " " + values.address).then(
                                            response => {
                                                const { lat, lng } = response.results[0].geometry.location;
                                                console.log(lat, lng);
                                                location.longitude = lng;
                                                location.latitude = lat;
                                            },
                                            error => {
                                                console.error(error);
                                            }
                                        );

                                        if (location.latitude == null || location.longitude == null) {
                                            location = null;
                                        } else {
                                            location.adress = fieldSetter(values.address);
                                            location.state = fieldSetter(values.country);
                                            location.town = fieldSetter(values.town);
                                        }
                                    }
                                }
                                call.phone = values.phone;
                                call.location = location;
                                call.tstmp = ((values.datetime === "") ? null : values.datetime);
                                call.description = values.req;

                                Service.sendRequest(call)
                                    .then((response) => {
                                        props.history.push("/home");
                                    }).catch((error) => {
                                        console.warn(error.message);
                                    })
                                console.log(call);
                            }}
                            validationSchema={Yup.object().shape({
                                req: Yup.string()
                                    .required("Obavezno unesite zahtjev"),
                                phone: Yup.number("Unos mora biti broj").required("Unesite broj mobitela"),
                                country: Yup.string(),

                            })}
                        >
                            {(props) => {
                                const {
                                    values,
                                    touched,
                                    errors,
                                    isSubmitting,
                                    handleChange,
                                    handleBlur,
                                    handleSubmit,
                                } = props;

                                return (
                                    <form onSubmit={handleSubmit}>
                                        <div>
                                            <div className={style.inp_line}>
                                                <label htmlFor="phone">*Broj mobitela
                                                <input
                                                        id="phone"
                                                        placeholder="Broj mobitela"
                                                        type="phone"
                                                        min="1" max="10"
                                                        value={values.phone}
                                                        onChange={handleChange}
                                                        onBlur={handleBlur}
                                                        className={
                                                            errors.phone && touched.phone
                                                                ? `${style.text_input} ${style.error}`
                                                                : style.text_input
                                                        }
                                                    /> {errors.phone && touched.phone && (
                                                        <div className={style.input_feedback}>{errors.phone}</div>
                                                    )}
                                                </label>
                                            </div>

                                            <div className={style.inp_line}>
                                                <label htmlFor="req">*Zahtjev:
                                                <textarea
                                                        id="req"
                                                        placeholder="Opis zahtjeva"
                                                        type="text"
                                                        value={values.req}
                                                        onChange={handleChange}
                                                        onBlur={handleBlur}
                                                        className={
                                                            errors.req && touched.rew
                                                                ? `${style.text_input} ${style.error}`
                                                                : style.text_input
                                                        } rows="3"></textarea>
                                                    {errors.req && touched.req && (
                                                        <div className={style.input_feedback}>{errors.req}</div>
                                                    )}
                                                </label>
                                            </div>

                                            <fieldset>
                                                <legend>Lokacija:</legend>
                                                <div className={style.inp_line}>
                                                    <label>Država:
                                                    <input
                                                            id="country"
                                                            placeholder="Država"
                                                            type="text"
                                                            value={values.country}
                                                            onChange={handleChange}
                                                            onBlur={handleBlur}
                                                            className={
                                                                errors.country && touched.country
                                                                    ? `${style.text_input} ${style.error}`
                                                                    : style.text_input
                                                            }
                                                        />
                                                        {errors.country && touched.country && (
                                                            <div className={style.input_feedback}>{errors.country}</div>
                                                        )}
                                                    </label>
                                                </div>

                                                <div className={style.inp_line}>
                                                    <label>Mjesto:
                                                    <input
                                                            id="town"
                                                            placeholder="Mjesto"
                                                            type="text"
                                                            value={values.town}
                                                            onChange={handleChange}
                                                            onBlur={handleBlur}
                                                            className={
                                                                errors.town && touched.town
                                                                    ? `${style.text_input} ${style.error}`
                                                                    : style.text_input
                                                            }
                                                        />
                                                    </label>
                                                </div>

                                                <div className={style.inp_line}>
                                                    <label>Adresa:
                                                    <input
                                                            id="address"
                                                            placeholder="Adresa"
                                                            type="text"
                                                            value={values.address}
                                                            onChange={handleChange}
                                                            onBlur={handleBlur}
                                                            className={
                                                                errors.address && touched.address
                                                                    ? `${style.text_input} ${style.error}`
                                                                    : style.text_input
                                                            }
                                                        />
                                                    </label>
                                                </div>
                                            </fieldset>

                                            <div className={style.inp_line}>

                                                <label htmlFor="datetime">Rok izvršenja:</label>
                                                <Datetime
                                                    id="datetime"
                                                    name="datetime"
                                                    onChange={(value) => onFieldChange(value, props)}
                                                    onBlur={() => onFieldBlur(props)}
                                                    initialValue={moment(moment.now())}
                                                    dateFormat="YYYY-MM-DD"
                                                    locale="hr"
                                                    timeFormat="HH:mm:ss"
                                                    isValidDate={isValidDate}
                                                />

                                            </div>
                                        </div>

                                        <div className={style.inp_line}>
                                            <label htmlFor="loc">Označite ako želite dodati vašu trenutnu lokaciju:</label>
                                            <input id="loc" type="checkbox" onClick={handleClick}>
                                            </input>

                                        </div>

                                        {checked ? <div>
                                            <GoogleMap mapContainerStyle={mapContainerStyle}
                                                zoom={8}
                                                center={{ lat: lat, lng: long }}
                                                options={options}
                                                onClick={(event) => {
                                                    setLat(event.latLng.lat())
                                                    setLong(event.latLng.lng())
                                                    Geocode.fromLatLng(event.latLng.lat(), event.latLng.lng()).then(
                                                        response => {
                                                            const add = response.results[0].formatted_address;
                                                            console.log(add);
                                                            setAddress(add);
                                                        },
                                                        error => {
                                                            console.error(error);
                                                        }
                                                    );
                                                }}
                                            >

                                                <Marker
                                                    key={16}
                                                    position={{ lat: lat, lng: long }}
                                                />
                                            </GoogleMap>
                                        </div> : null}

                                        <span className={style.input_feedback} id="uncategorised"></span>
                                        <div className={`${style.inp_line} ${style.lr_button_container}`}>
                                            <button type="submit" disabled={isSubmitting}>Pošalji zahtjev</button>
                                        </div>
                                    </form>
                                );
                            }}
                        </Formik>
                    </Card>
                </div>
            </div>
        </>
    );
};

export default Dash;
