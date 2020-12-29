import React, { useState } from 'react';
import { Card } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "./style/page.module.css";
import { Formik } from "formik";
import Sidebar from "./sidebar"
import * as Yup from "yup";
import style from "./style/log-reg.module.css";
import {
    GoogleMap,
    useLoadScript,
    Marker,
    InfoWindow,
} from "@react-google-maps/api";



const libraries = ["places"];
const mapContainerStyle = {
    height: "100vh",
    width: "60vw",
};
const center = {
    lat: 45.815399,
    lng: 15.966568,
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

    const onMapClick = React.useCallback((e) => {

        setLat(e.latLng.lat());
        setLong(e.latLng.lng());

        console.log(e.latLng.lat());
        console.log(e.latLng.lng());

    }, []);

    const mapRef = React.useRef();
    const onMapLoad = React.useCallback((map) => {
        mapRef.current = map;
    }, []);



    if (loadError) return "Error";
    if (!isLoaded) return "Loading...";

    document.body.style = 'background-image: none;';


    const handleClick = () => {
        console.log(checked);
        if (checked === false) {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition);
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

        setLong(position.coords.longitude)
        setLat(position.coords.latitude)


    }

    return (
        <>
            <Sidebar />
            <div className={style.empthy1}>            </div>
            <div className={style.container}>
                <Card className="crd col-lg-10 mx-auto">
                    <Formik
                        initialValues={{
                            adress: "",
                            phone: "",
                            date: "",
                            req: ""
                        }}
                        onSubmit={async (values) => {

                            let postData = new FormData();

                            postData.append("adress", values.adress);
                            postData.append("phone", values.phone);
                            postData.append("req", values.req);
                            postData.append("date", values.date);
                            if (checked === true) {
                                postData.append("long", long);
                                postData.append("lat", lat);
                            }




                            console.log(values, lat, long);

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
                    <GoogleMap mapContainerStyle={mapContainerStyle}
                        zoom={8}
                        center={center}
                        options={options}
                        onClick={(event) => {
                            console.log("kurac")
                            setLat(event.latLng.lat())
                            setLong(event.latLng.lng())
                        }}
                    >

                        <Marker
                            key={16}
                            position={{ lat: lat, lng: long }}
                        />
                    </GoogleMap>
                </Card>
            </div>
        </>
    );
};

export default Dash;
