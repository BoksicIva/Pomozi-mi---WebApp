import React from "react";
import "./log-reg.css";
import "../index.css";
import { Formik } from "formik";
import * as Yup from "yup";
import "bootstrap/dist/css/bootstrap.min.css";
import { Card } from "react-bootstrap";
import { Link } from "react-router-dom";

export const Registration = () => (
  <div className="app">
    <div className="empthy1"></div>
    <div className="container">
      <Card className="crd col-lg-7 mx-auto">
        <Card.Title className="title">
          Dobrodošli u aplikaciju <span className="pomozi">Pomozi mi</span>
        </Card.Title>
        <Formik
          initialValues={{
            name: "",
            surname: "",
            email: "",
            password: "",
            username: "",
          }}
          onSubmit={async (values) => {
            await new Promise((resolve) => setTimeout(resolve, 500));
            alert(JSON.stringify(values, null, 2));
          }}
          validationSchema={Yup.object().shape({
            email: Yup.string().email().required("Email required"),
            password: Yup.string().required("Password required"),
            username: Yup.string().min(5).max(10).required("Username required"),
            name: Yup.string().required("Name required"),
            surname: Yup.string().required("Surname required"),
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
                <div>
                  <div className="inp-line">
                    <label htmlFor="name">Name:</label>
                    <input
                      id="name"
                      placeholder="Enter your name"
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
                    <label htmlFor="surname">Surname:</label>
                    <input
                      id="surname"
                      placeholder="Enter your surname"
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
                    <label htmlFor="email">Email:</label>
                    <input
                      id="email"
                      placeholder="Enter your email"
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
                    <label htmlFor="username">Username:</label>
                    <input
                      id="username"
                      placeholder="Enter your username"
                      type="text"
                      value={values.username}
                      onChange={handleChange}
                      onBlur={handleBlur}
                      className={
                        errors.username && touched.username
                          ? "text-input error"
                          : "text-input"
                      }
                    />
                    {errors.username && touched.username && (
                      <div className="input-feedback">{errors.username}</div>
                    )}
                  </div>

                  <div className="inp-line">
                    <label htmlFor="password">Password:</label>
                    <input
                      id="password"
                      placeholder="Enter your password"
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
                </div>
                <div className="inp-line">
                  <span className="res-btn">
                    <button
                      type="button"
                      className="outline"
                      onClick={handleReset}
                      disabled={!dirty || isSubmitting}
                    >
                      Reset
                    </button>
                  </span>
                  <button type="submit" disabled={isSubmitting}>
                    Submit
                  </button>
                </div>
              </form>
            );
          }}
        </Formik>
        <div className="inp-line">
          Already signed in? <Link to="/login">Log in</Link>
        </div>
      </Card>
    </div>
  </div>
);

export default Registration;
