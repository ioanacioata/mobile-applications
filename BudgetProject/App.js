/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React from 'react';
import * as firebase from 'firebase'

import {StackNavigator} from 'react-navigation';
import HomeScreen from "./app/MainWindow";
import SeeItemScreen from "./app/SeeItem"
import AddItemScreen from "./app/AddItem";
import LoginScreen from "./app/LoginSreen";

global.supermarkets = [
    {name: "Profi Marasti"},
    {name: "Auchan Iulius Mall"},
    {name: "Kaufland Gheorgheni"},
    {name: "Carefour Vivo"}
];

const NavigationApp = StackNavigator({

    LoginScreen: {
        screen: LoginScreen,
    },

    Home: {
        screen: HomeScreen,
    },
    SeeItem: {
        path: 'seeItem/:item',
        screen: SeeItemScreen,
    },
    AddItem: {
        path: 'addItem',
        screen: AddItemScreen,
    }
});

export default class App extends React.Component {

    componentWillMount() {

        // Initialize Firebase
        const config = {
            apiKey: "AIzaSyB0j13WROZhQzq3pWPQWdUXOYj7VpwEHvI",
            authDomain: "budgetproject-23e24.firebaseapp.com",
            databaseURL: "https://budgetproject-23e24.firebaseio.com",
            projectId: "budgetproject-23e24",
            storageBucket: "",
            messagingSenderId: "944194239338"
        };

        firebase.initializeApp(config);
        // firebase.auth().signOut();
    }

    render() {
        return (
            <NavigationApp/>
        );
    }
};
