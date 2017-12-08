/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, {Component} from 'react';

import {StackNavigator} from 'react-navigation';
import HomeScreen from "./app/MainWindow";
import SeeItemScreen from "./app/SeeItem"
import AddItemScreen from "./app/AddItem";

global.products = [
    // {
    //     id: 1,
    //     name: 'Coca-Cola 0.5l',
    //     price: 2.5,
    //     supermarket: 'Auchan Iulis Mall',
    //     brand: 'Coca-Cola'
    // },

    // {
    //     id: 2,
    //     name: 'Cutie servetele',
    //     price: 5.5,
    //     supermarket: 'Lidl',
    //     brand: 'Cien'
    // },
    // {
    //     id: 3,
    //     name: 'Apa minerala 0.5l',
    //     price: 3.6,
    //     supermarket: 'Auchan Iulis Mall',
    //     brand: 'Aqua Carpatica'
    // },
    {
        id: 4,
        name: 'Ciocolata cu Oreo',
        price: 4.2,
        supermarket: 'Auchan Iulis Mall',
        brand: 'Milka'
    },
    {
        id: 5,
        name: 'Ciocolata cu Capsuni',
        price: 4.0,
        supermarket: 'Auchan Iulis Mall',
        brand: 'Milka'
    },
    {
        id: 6,
        name: 'Gummy Bears',
        price: 3.8,
        supermarket: 'Auchan Iulis Mall',
        brand: 'Haribo'
    }
];


const App = StackNavigator({
    Home: {
        screen: HomeScreen,
    },
    SeeItem: {
        path: 'seeItem/:item',
        screen: SeeItemScreen,
    },
    AddItem:{
        path:'addItem',
        screen: AddItemScreen,
    }
});

export default App;
