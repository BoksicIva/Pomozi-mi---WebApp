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
                            adress: "",
                            phone: "",
                            date: "",
                            req: "",
                            time: "",
                        }}
                        onSubmit={async (values) => {
                            /* 
                                                        let postData = new FormData();
                            
                                                        postData.append("adress", values.adress);
                                                        postData.append("phone", values.phone);
                                                        postData.append("req", values.req);
                                                        postData.append("date", values.date);
                                                        if (checked === true) {
                                                            postData.append("long", long);
                                                            postData.append("lat", lat);
                                                        } */
                            var call = new Object();
                            var location = new Object();
                            var addParts = address.split(",");
                            location.adress = addParts[0];
                            location.state = addParts[3].trim();
                            location.town = addParts[2].trim();
                            location.longitude = long;
                            location.latitude = lat;

                            call.phone = values.phone;
                            call.location = location;
                            call.tstmp = values.date + " " + "12:00:00";
                            call.description = values.req;

                            Service.sendRequest(call)
                                .then((response) => {
                                    props.history.push("/home");
                                }).catch((error) => {
                                    console.warn(error.message);
                                })


                            console.log(call, values.time);



                        }}
                        validationSchema={Yup.object().shape({
                            req: Yup.string()
                                .required("Unesite zahtjev"),
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
                                            <label htmlFor="phone">Broj mobitela</label>
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
                                                        :  style.text_input
                                                }
                                            />
                                        </div>

                                        <div className={style.inp_line}>
                                            <label htmlFor="adress">Adresa:</label>
                                            <input
                                                id="adress"
                                                placeholder="Adresa"
                                                type="text"
                                                value={values.adress}
                                                onChange={handleChange}
                                                onBlur={handleBlur}
                                                className={
                                                    errors.adress && touched.adress
                                                        ? `${style.text_input} ${style.error}`
                                                        : style.text_input
                                                }
                                            />
                                        </div>

                                        <div className={style.inp_line}>
                                            <label htmlFor="req">Zahtjev:</label>
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
                                        </div>

                                        <div className={style.inp_line}>
                                            <label htmlFor="date">Rok izvršenja:</label>
                                            <input
                                                id="date"

                                                type="date"
                                                value={values.date}
                                                onChange={handleChange}
                                                onBlur={handleBlur}
                                            />
                                        </div>

                                        <div className={style.inp_line}>
                                            <label htmlFor="date">Označite ako želite dodati vašu trenutnu lokaciju:</label>
                                            <input type="checkbox" onClick={handleClick}>
                                            </input>

                                        </div>
                                    </div>
                                    {checked ? <div>
                                        <GoogleMap mapContainerStyle={mapContainerStyle}
                                            zoom={8}
                                            center={{lat: lat, lng: long }}
                                            options={options}
                                            onClick={(event) => {
                                                console.log("kurac")
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

                                        <button type="submit" disabled={isSubmitting}>
                                            Pošalji zahtjev
                                  </button>
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
