import React from "react";
import './log-reg.css';
import '../index.css';
import { Formik } from "formik";
import * as Yup from "yup";
import 'bootstrap/dist/css/bootstrap.min.css';
import { Card } from 'react-bootstrap';
import {Link} from 'react-router-dom';
import LoginService from "../service/login-service";



export const Login = () => (
    <div className="app">
        <div className="empthy"></div>
        <div className="container">
            <Card className="crd col-lg-7 mx-auto">
                <Card.Title className="title">Prijavi se u aplikaciju <span className="pomozi">Pomozi mi</span></Card.Title>
                <Formik
                    initialValues={{
                        email: "",
                        password: ""
                    }}
                    onSubmit={async values => {
                          await new Promise(resolve => setTimeout(resolve, 500));
                        alert(JSON.stringify(values, null, 2));
                     /*  let formData = new FormData();
  
                       formData.append("email", values.email);
                       formData.append("password", values.password);

                       LoginService.login(formData).then((response) => {
                        //   const token = response.headers[];
                        });
                        */

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
                                    
                                    <div className="inp-line">
                                        <label htmlFor="password">
                                            Password:
                                    </label>
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
                                            <div className="input-feedback">
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
                <div className="inp-line">not signed in yet? <Link to='/register'>Sign in</Link></div>
            </Card>
        </div>
    </div>
);

export default Login;    