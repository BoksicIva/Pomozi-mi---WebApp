import React from "react";
import "./log-reg.css";
import "../index.css";
import { Formik } from "formik";
import * as Yup from "yup";
import "bootstrap/dist/css/bootstrap.min.css";
import { Card } from "react-bootstrap";
import { Link } from "react-router-dom";
import RegService from "../service/login-service";

export const Registration = () => (
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
          }}
          onSubmit={async (values) => {
              await new Promise((resolve) => setTimeout(resolve, 500));

              alert(JSON.stringify(values, null, 2));

              let data = new FormData();

              data.append("firstName", "Jan");
              data.append("lastName", "Roček");
              data.append("password", "JanRoček1@");
              data.append("secondPassword", "JanRoček1@");
              data.append("email", "jan.rocek@gmail.com");

              RegService.getCSRF()
                  .then((response) => {
                      console.log(response);
                      console.log(response.data);
                      console.log(response.headers);

                      RegService.register(data)
                          .then((response1) => {
                              // alert(JSON.stringify(response1, null, 2));
                              props.history.push('/login');
                          })
                          .catch((error1) => {
                              console.log(error1);
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
              .matches(/^[a-zA-Z]+$/, "Ime smije sadržavati samo slova")
              .required("Unesite ime"),
            lastName: Yup.string()
              .min(2, "Prekratko prezime")
              .matches(/^[a-zA-Z]+$/, "Prezime smije sadržavati samo slova")
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
                <div className="form-fields">
                  <div className="inp-line">
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
                  </div>

                  <div className="inp-line">
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
                  </div>

                  <div className="inp-line">
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
                  </div>

                  <div className="inp-line">
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
                  </div>

                  <div className="inp-line">
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
                  </div>
                </div>

                <div className="inp-line">
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
