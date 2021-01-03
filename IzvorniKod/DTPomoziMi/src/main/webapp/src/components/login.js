import React from "react";
import style from "./style/log-reg.module.css";
import "../index.module.css";
import { Formik } from "formik";
import * as Yup from "yup";
import "bootstrap/dist/css/bootstrap.min.css";
import { Card } from "react-bootstrap";
import { Link } from "react-router-dom";
import LoginService from "../service/login-service";

export const Login = (props) => (
  <div className={style.background}>
    <div className={style.empthy}></div>
    <div className="container">
      <Card className="crd col-lg-7 mx-auto">
        <Card.Title className={style.title}>
          Prijavi se u aplikaciju <span style={{ color: "red" }}>Pomozi</span>Mi
        </Card.Title>
        <Formik
          initialValues={{
            email: "",
            password: "",
          }}
          onSubmit={async (values) => {


            let formData = new FormData();

            formData.append("email", values.email);
            formData.append("password", values.password);



            LoginService.login(formData)
              .then((response) => {
                //alert(JSON.stringify(response, null, 2));
                props.history.push("/home");
              })


          }
          }
          validationSchema={Yup.object().shape({
            email: Yup.string()
              .email("Unesite e-mail ispravnog formata")
              .required("Unesite e-mail"),
            password: Yup.string().required("Unesite zaporku"),
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
                  <div className={style.inp_line}>
                    <label htmlFor="email">E-mail:</label>
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
                  </div>

                  <div className={style.inp_line}>
                    <label htmlFor="password">Zaporka:</label>
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
                  </div>
                </div>

                <span className={style.input_feedback} id="uncategorised"></span>
                <div className="inp-line lr-button-container">
                  <button
                    type="button"
                    className={style.outline}
                    onClick={handleReset}
                    disabled={!dirty || isSubmitting}
                  >
                    Resetiraj
                  </button>

                  <button type="submit" disabled={isSubmitting}>
                    Prijavi se
                  </button>
                </div>
              </form>
            );
          }}
        </Formik>
        <div className={style.inp_line}>
          Niste registrirani? <Link to="/register">Registracija</Link>
        </div>
      </Card>
    </div>
  </div>
);

export default Login;
