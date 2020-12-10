import React from "react";
import "./log-reg.css";
import "../index.css";
import { Formik } from "formik";
import * as Yup from "yup";
import "bootstrap/dist/css/bootstrap.min.css";
import { Card } from "react-bootstrap";
import { Link } from "react-router-dom";
import RegService from "../service/login-service";

export const Registration = (props) => (
  <div className="app">
    <div className="empthy1"></div>
    <div className="container">
      <Card className="crd col-lg-7 mx-auto">
        <Card.Title className="title">
          Izradite svoj <span style={{ color: "red" }}>Pomozi</span>Mi račun
        </Card.Title>
        <Formik
          initialValues={{
            firstName: "",
            lastName: "",
            email: "",
            password: "",
            secondPassword: "",
            error1: "",
          }}
          onSubmit={async (values) => {
            await new Promise((resolve) => setTimeout(resolve, 500));

            let data = new FormData();

            data.append("firstName", values.firstName);
            data.append("lastName", values.lastName);
            data.append("password", values.password);
            data.append("secondPassword", values.secondPassword);
            data.append("email", values.email);

            RegService.getCSRF()
              .then((response) => {
                console.log(response);
                console.log(response.data);
                console.log(response.headers);

                RegService.register(data)
                  .then((response1) => {
                    props.history.push("/login");
                  })
                  .catch((error1) => {
                    const code = error1.response.status;
                    const response = error1.response.data;

                    if (code === 400) {
                      for (let obj of response) {
                        let div = document.createElement("div");
                        for(let s of obj.defaultMessage.split("] | [")) {
                          div.innerHTML += s + "</br>";
                        }
                        
                        if (obj.field !== undefined) {
                          document
                            .getElementById(obj.field + "-error")
                            .append(div);
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
              })
              .catch((error) => {
                console.log(error);
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
                <div className="inp-line">
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
                        ? "text-input error"
                        : "text-input"
                    }
                  />
                  {errors.firstName && touched.firstName && (
                    <div className="input-feedback">{errors.firstName}</div>
                  )}
                  <span className="input-feedback" id="firstName-error"></span>
                </div>

                <div className="inp-line">
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
                        ? "text-input error"
                        : "text-input"
                    }
                  />
                  {errors.lastName && touched.lastName && (
                    <div className="input-feedback">{errors.lastName}</div>
                  )}
                  <span className="input-feedback" id="lastName-error"></span>
                </div>

                <div className="inp-line">
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
                        ? "text-input error"
                        : "text-input"
                    }
                  />
                  {errors.email && touched.email && (
                    <div className="input-feedback">{errors.email}</div>
                  )}
                  <span className="input-feedback" id="email-error"></span>
                </div>

                <div className="inp-line">
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
                        ? "text-input error"
                        : "text-input"
                    }
                  />
                  {errors.password && touched.password && (
                    <div className="input-feedback">{errors.password}</div>
                  )}
                  <span className="input-feedback" id="password-error"></span>
                </div>

                <div className="inp-line">
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
                        ? "text-input error"
                        : "text-input"
                    }
                  />
                  {errors.secondPassword && touched.secondPassword && (
                    <div className="input-feedback">
                      {errors.secondPassword}
                    </div>
                  )}
                  <span
                    className="input-feedback"
                    id="secondPassword-error"
                  ></span>
                </div>

                <div className="inp-line">
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
                        ? "text-input error"
                        : "text-input"
                    }
                  />
                  {errors.country && touched.country && (
                    <div className="input-feedback">{errors.country}</div>
                  )}
                </div>

                <div className="inp-line">
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
                        ? "text-input error"
                        : "text-input"
                    }
                  />
                  {errors.town && touched.town && (
                    <div className="input-feedback">{errors.town}</div>
                  )}
                </div>

                <div className="inp-line">
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
                        ? "text-input error"
                        : "text-input"
                    }
                  />
                  {errors.address && touched.address && (
                    <div className="input-feedback">{errors.address}</div>
                  )}
                </div>

                <span className="input-feedback" id="uncategorised"></span>
                <div className="inp-line lr-button-container">
                  <span className="res-btn">
                    <button
                      type="button"
                      className="outline"
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
        <div className="inp-line">
          Već imate račun? <Link to="/login">Prijavite se</Link>
        </div>
      </Card>
    </div>
  </div>
);

export default Registration;
