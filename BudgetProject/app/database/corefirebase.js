import * as firebase from 'firebase'

// Initialize Firebase
const config = {
    apiKey: "AIzaSyB0j13WROZhQzq3pWPQWdUXOYj7VpwEHvI",
    authDomain: "budgetproject-23e24.firebaseapp.com",
    databaseURL: "https://budgetproject-23e24.firebaseio.com",
    projectId: "budgetproject-23e24",
    storageBucket: "",
    messagingSenderId: "944194239338"
};

const firebaseApp = firebase.initializeApp(config);
const firebasedb = firebaseApp.database();

export const productsRef = firebasedb.ref('/Products');