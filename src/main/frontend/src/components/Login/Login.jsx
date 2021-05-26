import React, {useState} from "react";
import {useHistory} from "react-router";
import {useLocale} from "../LoginContext";
import {withNamespaces} from 'react-i18next';
import BreadCrumb from "../BreadCrumb";
import {Link} from "react-router-dom";
import {api} from "../../Api";
import {Form, Formik} from 'formik';
import "../../css/Login.css"
import {validatorFactory, ValidatorType} from "../Validation/Validators";
import {useNotificationWarningAndLong} from "../Notification/NotificationProvider";
import {dialogDuration, dialogType} from "../Notification/Notification";
import axios from "axios";
import jwt_decode from "jwt-decode";
import {useNotificationCustom} from "../Notification/NotificationProvider";
import LoginFieldComponent from "./LoginFieldComponent";
import FieldComponent from "../PasswordReset/FieldComponent";

const REFRESH_TIME = 60 * 1000;

function Login(props) {
    const dispatchWarningNotification = useNotificationWarningAndLong();
    const {t,i18n} = props
    const history = useHistory();
    const {token, setToken, saveToken} = useLocale();

    const dispatch = useNotificationCustom();

    const handleRefreshBox = () => {
        dispatch({
            "type": dialogType.WARNING,
            "duration": dialogDuration.MINUTE,
            "title": `${i18n.t('dialog.expiring_token.title')}`,
            "message":
                (<> {i18n.t('dialog.expiring_token.message')} <span className={"text-primary"}
                                                                    style={{cursor: "pointer"}}
                                                                    onClick={refreshToken}>{i18n.t('dialog.expiring_token.refresh')}</span></>)
        })
    }

    const refreshToken = (event) => {
        event.target.closest(".alert").querySelector(".close").click();

        axios.post(`${process.env.REACT_APP_API_BASE_URL}resources/auth/refresh-token`, localStorage.getItem("token"), {
            headers: {
                "Authorization": `${localStorage.getItem("token")}`
            }
        }).then(res => res.data)
            .then(token => saveToken("Bearer " + token));
        setTimeout(() => {
            schedule();
        }, 1000)
    }

    const handleSubmit = async (values) => {
        try {
            const res = await api.login({
                login: values.login,
                password: values.password
            })
            saveToken("Bearer " + res.data)
            history.push("/userPage")
        } catch (ex) {
            console.log(ex);
        }
        schedule();
    }

    const schedule = () => {
        return setTimeout(() => {
            handleRefreshBox();
        }, new Date(jwt_decode(localStorage.getItem("token")).exp * 1000) - new Date() - REFRESH_TIME);
    }

    return (
        <div className="container">
            <BreadCrumb>
                <li className="breadcrumb-item"><Link to="/">{t('mainPage')}</Link></li>
                <li className="breadcrumb-item active" aria-current="page">{t('logging')}</li>
            </BreadCrumb>
            <div className="floating-box">
                <Formik initialValues={{login: '', password: ''}}
                        validate={values => {
                            const errors = {};

                            if (!values.login) {
                                errors.login = t('LoginForm.error.login.required');
                            } else {
                                validatorFactory(values.login, ValidatorType.LOGIN).forEach(x => {
                                    errors.login = x;
                                })
                            }

                            if (!values.password) {
                                errors.password = t('LoginForm.error.password.required');
                            } else {
                                validatorFactory(values.password, ValidatorType.PASSWORD).forEach(x => {
                                    errors.password = x;
                                })
                            }

                            return errors;
                        }}
                        onSubmit={values => {
                            handleSubmit(values);
                        }}>

                    {({isSubmitting, handleChange}) => (
                        <Form className={{alignItems: "center"}}>
                            <LoginFieldComponent name="login" placeholder={t('login')}
                                                 handleChange={handleChange}/>
                            <FieldComponent name="password" placeholder={t('password')}
                                            handleChange={handleChange}/>
                            <button className="btn btn-lg btn-primary btn-block mt-2"
                                    type="submit" disabled={isSubmitting}
                                    style={{backgroundColor: "#7749F8", width: "70%", margin: "auto"}}>
                                {t('signIn')}
                            </button>
                            <button className="btn btn-lg btn-primary btn-block mt-2" type="button"
                                    onClick={() => history.push("/login/password-reset")} style={{backgroundColor: "#7749F8", width: "70%", margin: "auto"}}>
                                {t('passwordReset')}
                            </button>
                        </Form>
                    )}
                </Formik>
            </div>
        </div>
    );
}

export default withNamespaces()(Login);