import React, { useState } from 'react';
import { Form, Card } from "react-bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import "./style/page.css";
import { Formik } from "formik";
import Sidebar from "./sidebar"
import * as Yup from "yup";
import "./style/log-reg.css";




export const Dash = props => {

    document.body.style = 'background-image: none;';
    const [long, setLong] = useState("");
    const [lat, setLat] = useState("");
    const [checked, setchecked] = useState(false);

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
            <div className="empthy1">            </div>
            <div className="container">
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
                                dirty,
                                isSubmitting,
                                handleChange,
                                handleBlur,
                                handleSubmit,
                            } = props;
                            return (
                                <form onSubmit={handleSubmit}>
                                    <div>
                                        <div className="inp-line">
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
                                                        ? "text-input error"
                                                        : "text-input"
                                                }
                                            />
                                        </div>

                                        <div className="inp-line">
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
                                                        ? "text-input error"
                                                        : "text-input"
                                                }
                                            />
                                        </div>

                                        <div className="inp-line">
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
                                                        ? "text-input error"
                                                        : "text-input"
                                                } rows="3"></textarea>
                                        </div>

                                        <div className="inp-line">
                                            <label htmlFor="date">Rok izvršenja:</label>
                                            <input
                                                id="date"

                                                type="date"
                                                value={values.date}
                                                onChange={handleChange}
                                                onBlur={handleBlur}
                                            />
                                        </div>

                                        <div className="inp-line">
                                            <label htmlFor="date">Označite ako želite dodati vašu trenutnu lokaciju:</label>
                                            <input type="checkbox" onClick={handleClick}>
                                            </input>

                                        </div>
                                    </div>

                                    <span className="input-feedback" id="uncategorised"></span>
                                    <div className="inp-line lr-button-container">

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

        </>
    );
};

export default Dash;
