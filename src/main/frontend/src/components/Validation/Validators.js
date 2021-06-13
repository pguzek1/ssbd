import React from 'react';
import i18n from "../../i18n";

const sizeValidator = (data, minInclusive, maxInclusive) => {
    if (data.length < minInclusive) {
        return i18n.t("form.validation.field.min_size");
    }
    if (data.length > maxInclusive) {
        return i18n.t("form.validation.field.max_size");
    }
}

const patternValidator = (data, pattern) => {
    if (!pattern.test(data)) {
        return i18n.t("form.validation.field.pattern");
    }
}

const allowedListValidator = (data, list) => {
    if (!list.includes(data)) {
        return i18n.t("form.validation.field.not_allowed");
    }
}

export const validateContactNumber = (data) => {
    let errors = [];
    errors.push(sizeValidator(data, 9, 15));
    errors.push(patternValidator(data, /^[0-9\+][0-9]{8,14}$/));
    return errors.filter(err => err !== undefined);
}

export const validateFirstname = (data) => {
    let errors = [];
    errors.push(sizeValidator(data, 3, 31));
    errors.push(patternValidator(data, /^[A-ZĆŁÓŚŹŻ\s]{1}[a-ząęćńóśłźż]+$/));
    return errors.filter(err => err !== undefined);
}

export const validateLanguage = (data) => {
    let errors = [];
    errors.push(sizeValidator(data, 2, 2));
    errors.push(patternValidator(data, /^[a-z]{2}$/));
    errors.push(allowedListValidator(data, ["pl", "en"]));
    return errors.filter(err => err !== undefined);
}

export const validateLastname = (data) => {
    let errors = [];
    errors.push(sizeValidator(data, 2, 31));
    errors.push(patternValidator(data, /^[A-ZĆŁÓŚŹŻ\s]{1}[a-ząęćńóśłźż]+$/));
    return errors.filter(err => err !== undefined);
}

export const validateLogin = (data) => {
    let errors = [];
    errors.push(sizeValidator(data, 3, 19));
    errors.push(patternValidator(data, /^[a-zA-Z0-9]+$/));
    return errors.filter(err => err !== undefined);
}

export const validatePassword = (data) => {
    let errors = [];
    errors.push(sizeValidator(data, 8, 64));
    errors.push(patternValidator(data, /^(?=.*[A-Z])(?=.*[!@#$&*])(?=.*[0-9])(?=.*[a-z]).{8,64}$/));
    return errors.filter(err => err !== undefined);
}

export const validatePenCode = (data) => {
    let errors = [];
    errors.push(sizeValidator(data, 32, 32));
    errors.push(patternValidator(data, /^[0-9a-fA-F]{8}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{4}\-[0-9a-fA-F]{12}$/));
    return errors.filter(err => err !== undefined);
}

export const validateUserEmail = (data) => {
    let errors = [];
    errors.push(sizeValidator(data, 6, 127));
    errors.push(patternValidator(data, /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/));
    return errors.filter(err => err !== undefined);
}

export const validateHotelName = (data) => {
    let errors = [];
    errors.push(sizeValidator(data, 2, 63));
    errors.push(patternValidator(data, /^[A-ZĆŁÓŚŹŻa-ząęćńóśłźża-zA-Z0-9,\s\\-]+$/));
    return errors.filter(err => err !== undefined);
}

export const validateAddress = (data) => {
    let errors = [];
    errors.push(sizeValidator(data, 2, 63));
    errors.push(patternValidator(data, /^[A-ZĆŁÓŚŹŻa-ząęćńóśłźża-zA-Z0-9,.\s\\-]+$/));
    return errors.filter(err => err !== undefined);
}

export const ValidatorType = {
    CONTACT_NUMBER: "CONTACT_NUMBER",
    FIRSTNAME: "FIRSTNAME",
    LANGUAGE: "LANGUAGE",
    LASTNAME: "LASTNAME",
    LOGIN: "LOGIN",
    PASSWORD: "PASSWORD",
    PEN_CODE: "PEN_CODE",
    USER_EMAIL: "USER_EMAIL",
    HOTEL_NAME: "HOTEL_NAME",
    ADDRESS: "ADDRESS",
};

export const validatorFactory = (data, validatorType) => {
    switch (validatorType) {
        case ValidatorType.CONTACT_NUMBER:
            return validateContactNumber(data);
        case ValidatorType.FIRSTNAME:
            return validateFirstname(data);
        case ValidatorType.LANGUAGE:
            return validateLanguage(data);
        case ValidatorType.LASTNAME:
            return validateLastname(data);
        case ValidatorType.LOGIN:
            return validateLogin(data);
        case ValidatorType.PASSWORD:
            return validatePassword(data);
        case ValidatorType.PEN_CODE:
            return validatePenCode(data);
        case ValidatorType.USER_EMAIL:
            return validateUserEmail(data);
        case ValidatorType.HOTEL_NAME:
            return validateHotelName(data);
        case ValidatorType.ADDRESS:
            return validateAddress(data);
        default:
            return [];
    }
}
