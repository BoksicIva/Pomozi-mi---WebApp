import React from "react";
import style from "./style/log-reg.module.css";
import "../index.module.css";
import { Formik } from "formik";
import * as Yup from "yup";
import "bootstrap/dist/css/bootstrap.min.css";
import { Card } from "react-bootstrap";
import { Link } from "react-router-dom";
import RegService from "../service/login-service";
import Geocode from "react-geocode";

Geocode.setApiKey(process.env.REACT_APP_GOOGLE_MAPS_API_KEY);


export const Registration = (props) => (
  <div className={style.app}>
    <div className={style.empthy1}></div>
    <div className={style.container}>
      <Card className="crd col-lg-7 mx-auto">
        <Card.Title className={style.title}>
          Izradite svoj <span style={{ color: "red" }}>Pomozi</span>Mi račun
        </Card.Title>
        <Formik
          initialValues={{
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            secondPassword: "",
            country: "",
            town: "",
            address: "",
            error1: "",
          }}
          onSubmit={async (values) => {
            /* if (values.email !== localStorage.getItem("email"))
              localStorage.removeItem("photo");

            localStorage.setItem("firstName", values.firstName);
            localStorage.setItem("lastName", values.lastName);
            localStorage.setItem("email", values.email);
            localStorage.setItem("country", values.country);
            localStorage.setItem("town", values.town);
            localStorage.setItem("address", values.address); */

            let data = new FormData();

            Geocode.fromAddress(values.address).then(
              response => {
                const { lat, lng } = response.results[0].geometry.location;
                console.log(lat, lng);
                data.append("longitude", lng);
                data.append("latitude", lat)
              },
              error => {
                console.error(error);
              }
            );

            data.append("firstName", values.firstName);
            data.append("lastName", values.lastName);
            data.append("password", values.password);
            data.append("secondPassword", values.secondPassword);
            data.append("email", values.email);

            RegService.register(data)
              .then((response1) => {
                props.history.push("/login");
              })
              .catch((error1) => {
                const code = error1.status;
                const response = error1.data;

                if (code === 400) {
                  for (let obj of response) {
                    let div = document.createElement("div");
                    for (let s of obj.defaultMessage.split("] | [")) {
                      div.innerHTML += s + "</br>";
                    }

                    if (obj.field !== undefined) {
                      document.getElementById(obj.field + "-error").append(div);
                    } else {
                      document.getElementById("uncategorised").append(div);
                    }
                  }
                }
                if (code === 403) {
                  let div = document.createElement("div");
                  div.innerHTML = response;
                  document.getElementById("uncategorised").append(div);
                }
              });
          }}
          validationSchema={Yup.object().shape({
            email: Yup.string()
              .email("Unesite e-mail ispravnog formata")
              .required("Unesite e-mail"),
            password: Yup.string()
              .min(8, "Zaporka mora imati barem 8 znakova")
              .required("Unesite zaporku"),
            secondPassword: Yup.string()
              .oneOf([Yup.ref("password")], "Potvrda mora biti jednaka zaporci")
              .required("Unesite potvrdu"),
            firstName: Yup.string()
              .min(2, "Prekratko ime")
              .required("Unesite ime"),
            lastName: Yup.string()
              .min(2, "Prekratko prezime")
              .required("Unesite prezime"),
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
              handleReset,
            } = props;
            return (
              <form onSubmit={handleSubmit}>
                <div className={style.inp_line}>
                  <label>Ime*:</label>
                  <input
                    id="firstName"
                    placeholder="Ime"
                    type="text"
                    value={values.firstName}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={
                      errors.firstName && touched.firstName
                        ? `${style.text_input} ${style.error}`
                        : style.text_input
                    }
                  />
                  {errors.firstName && touched.firstName && (
                    <div className={style.input_feedback}>{errors.firstName}</div>
                  )}
                  <span className={style.input_feedback} id="firstName-error"></span>
                </div>

                <div className={style.inp_line}>
                  <label>Prezime*:</label>
                  <input
                    id="lastName"
                    placeholder="Prezime"
                    type="text"
                    value={values.lastName}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={
                      errors.lastName && touched.lastName
                        ? `${style.text_input} ${style.error}`
                        : style.text_input
                    }
                  />
                  {errors.lastName && touched.lastName && (
                    <div className={style.input_feedback}>{errors.lastName}</div>
                  )}
                  <span className={style.input_feedback} id="lastName-error"></span>
                </div>

                <div className={style.inp_line}>
                  <label>E-mail*:</label>
                  <input
                    id="email"
                    placeholder="E-mail"
                    type="text"
                    value={values.email}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={
                      errors.email && touched.email
                        ? `${style.text_input} ${style.error}`
                        : style.text_input
                    }
                  />
                  {errors.email && touched.email && (
                    <div className={style.input_feedback}>{errors.email}</div>
                  )}
                  <span className={style.input_feedback} id="email-error"></span>
                </div>

                <div className={style.inp_line}>
                  <label>Zaporka*:</label>
                  <input
                    id="password"
                    placeholder="Zaporka"
                    type="password"
                    value={values.password}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={
                      errors.password && touched.password
                        ? `${style.text_input} ${style.error}`
                        : style.text_input
                    }
                  />
                  {errors.password && touched.password && (
                    <div className={style.input_feedback}>{errors.password}</div>
                  )}
                  <span className={style.input_feedback} id="password-error"></span>
                </div>

                <div className={style.inp_line}>
                  <label>Potvrdi*:</label>
                  <input
                    id="secondPassword"
                    placeholder="Potvrdi"
                    type="password"
                    value={values.secondPassword}
                    onChange={handleChange}
                    onBlur={handleBlur}
                    className={
                      errors.secondPassword && touched.secondPassword
                        ? `${style.text_input} ${style.error}`
                        :  style.text_input
                    }
                  />
                  {errors.secondPassword && touched.secondPassword && (
                    <div className={style.input_feedback}>
                      {errors.secondPassword}
                    </div>
                  )}
                  <span
                    className={style.input_feedback}
                    id="secondPassword-error"
                  ></span>
                </div>

                <div className={style.inp_line}>
                  <label>Država:</label>
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
                </div>

                <div className={style.inp_line}>
                  <label>Mjesto:</label>
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
                  {errors.town && touched.town && (
                    <div className={style.input_feedback}>{errors.town}</div>
                  )}
                </div>

                <div className={style.inp_line}>
                  <label>Adresa:</label>
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
                  {errors.address && touched.address && (
                    <div className={style.input_feedback}>{errors.address}</div>
                  )}
                </div>

                <span className={style.input_feedback} id="uncategorised"></span>
                <div className={`${style.inp_line} ${style.lr_button_container}`}>
                  <span className={style.res_btn}>
                    <button
                      type="button"
                      className={style.outline}
                      onClick={handleReset}
                      disabled={!dirty || isSubmitting}
                    >
                      Resetiraj
                    </button>
                  </span>
                  <button type="submit" disabled={isSubmitting}>
                    Registracija
                  </button>
                </div>
              </form>
            );
          }}
        </Formik>
        <div className={style.inp_line}>
          Već imate račun? <Link to="/login">Prijavite se</Link>
        </div>
      </Card>
    </div>
  </div>
);

export default Registration;
