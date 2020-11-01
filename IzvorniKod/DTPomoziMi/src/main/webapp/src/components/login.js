import React from "react";
import './login.css';
import { Formik } from "formik";
import * as Yup from "yup";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Card } from 'react-bootstrap';


export const Login = () => (
    <div className="app">
        <div className="empthy"></div>
        <div className="container">
            <Card className="crd col-lg-7">
                <Card.Title className="title">Dobrodošli u aplikaciju <strong>Pomozi mi</strong></Card.Title>
                <Formik
                    initialValues={{
                        email: "",
                        password: ""
                    }}
                    onSubmit={async values => {
                        await new Promise(resolve => setTimeout(resolve, 500));
                        alert(JSON.stringify(values, null, 2));
                    }}
                    validationSchema={Yup.object().shape({
                        email: Yup.string()
                            .email()
                            .required("Required"),
                        password: Yup.string().required("password required")
                    })}
                >
                    {props => {
                        const {
                            values,
                            touched,
                            errors,
                            dirty,
                            isSubmitting,
                            handleChange,
                            handleBlur,
                            handleSubmit,
                            handleReset
                        } = props;
                        return (
                            <form onSubmit={handleSubmit}>
                                <div>
                                    <div className="inp-line">
                                        <label htmlFor="email">
                                            Email:
                                    </label>
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
                                    <br />
                                    <div className="inp-line">
                                        <label htmlFor="password">
                                            Password:
                                    </label>
                                        <input
                                            id="password"
                                            placeholder="Enter your password"
                                            type="text"
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
                                            <div className="input-feedback">
                                                <span className="emt-err">    </span>
                                                {errors.password}
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
                                    Reset
                                </button>
                                </span>
                                <button type="submit"  disabled={isSubmitting}>
                                    Submit
                                </button>
                                </div>

                            </form>
                        );
                    }}
                </Formik>
            </Card>
        </div>
    </div>
);

export default Login;    