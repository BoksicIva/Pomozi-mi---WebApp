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
                <Card.Title className="title">
                Prijavi se u aplikaciju <span style={{ color: "red" }}>Pomozi</span>Mi
                </Card.Title>
                <Formik
                    initialValues={{
                        email: "",
                        password: ""
                    }}
                    onSubmit={async values => {
                          await new Promise(resolve => setTimeout(resolve, 500));
                          alert(JSON.stringify(values, null, 2));
                       let formData = new FormData();
                       
                       formData.append('email', 'jan.rocek@gmail.com');
                       formData.append('password', 'JanRoček1@');

                        LoginService.getCSRF().then((response) => {
                            console.log(response);
                            console.log(response.data)
                            console.log(response.headers);
                        }).catch((error) => {
                            console.log(error);
                        });

                         LoginService.login(formData).then((response) => {
                            alert(JSON.stringify(response, null, 2));

                            LoginService.notPer().then((response2) => {
                                alert(JSON.stringify(response2, null, 2));
                            }).catch((error2) => {
                                console.log(error2);
                            });
                        }).catch((error) => {
                            console.log(error);
                        });
                        

                    }}
                    
                    validationSchema={Yup.object().shape({
                        email: Yup.string()
                            .email("Unesite e-mail ispravnog formata")
                            .required("Ovo polje je obavezno"),
                        password: Yup.string("Ovo polje je obavezno").required("Ovo polje je obavezno")
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
                                            placeholder="Upisite svoj email"
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
                                            Lozinka:
                                    </label>
                                        <input
                                            id="password"
                                            placeholder="Upisite svoju lozinku"
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
                                
                                <button
                                    type="button"
                                    className="outline"
                                    onClick={handleReset}
                                    disabled={!dirty || isSubmitting}
                                >
                                    Resetiraj
                                </button>
                                
                                <button type="submit"  disabled={isSubmitting}>
                                    Prijavi se
                                </button>
                                </div>

                            </form>
                        );
                    }}
                </Formik>
                <div className="inp-line">Niste registrirani? <Link to='/register'>Registracija</Link></div>
            </Card>
        </div>
    </div>
);

export default Login;    