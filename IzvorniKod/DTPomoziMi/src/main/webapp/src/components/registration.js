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
            name: "",
            surname: "",
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
                    alert(JSON.stringify(response1, null, 2));
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
            email: Yup.string().email().required("Unesite e-mail"),
            password: Yup.string().required("Unesite zaporku"),
            secondPassword: Yup.string().required(
              "Unesite potvrdu"
            ),
            name: Yup.string().required("Unesite ime"),
            surname: Yup.string().required("Unesite prezime"),
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
                      id="name"
                      placeholder="Ime"
                      type="text"
                      value={values.name}
                      onChange={handleChange}
                      onBlur={handleBlur}
                      className={
                        errors.name && touched.name
                          ? "text-input error"
                          : "text-input"
                      }
                    />
                    {errors.name && touched.name && (
                      <div className="input-feedback">{errors.name}</div>
                    )}
                  </div>

                  <div className="inp-line">
                    <input
                      id="surname"
                      placeholder="Prezime"
                      type="text"
                      value={values.surname}
                      onChange={handleChange}
                      onBlur={handleBlur}
                      className={
                        errors.surname && touched.surname
                          ? "text-input error"
                          : "text-input"
                      }
                    />
                    {errors.surname && touched.surname && (
                      <div className="input-feedback">{errors.surname}</div>
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
                      type="text"
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
