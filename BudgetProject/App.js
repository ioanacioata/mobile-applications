/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React from 'react';

import {StackNavigator} from 'react-navigation';
import HomeScreen from "./app/MainWindow";
import SeeItemScreen from "./app/SeeItem"
import AddItemScreen from "./app/AddItem";

global.supermarkets = [
    {name: "Profi Marasti"},
    {name:"Auchan Iulius Mall"},
    {name:"Kaufland Gheorgheni"},
    {name:"Carefour Vivo"}
];

global.products = [];
//     {
//         id: 1,
//         name: 'Coca-Cola 0.5l',
//         price: 2.5,
//         supermarket: global.supermarkets[0],
//         brand: 'Coca-Cola'
//     },
//
//     {
//         id: 2,
//         name: 'Cutie servetele',
//         price: 5.5,
//         supermarket: global.supermarkets[1],
//         brand: 'Cien'
//
//     },
//     {
//         id: 3,
//         name: 'Apa minerala 0.5l',
//         price: 3.6,
//         supermarket: global.supermarkets[2],
//         brand: 'Aqua Carpatica'
//     },
//     {
//         id: 4,
//         name: 'Ciocolata cu Oreo',
//         price: 4.2,
//         supermarket: global.supermarkets[1],
//         brand: 'Milka'
//     },
//     {
//         id: 5,
//         name: 'Ciocolata cu Capsuni',
//         price: 4.0,
//         supermarket: global.supermarkets[3],
//         brand: 'Milka'
//     },
//     {
//         id: 6,
//         name: 'Gummy Bears',
//         price: 3.8,
//         supermarket: global.supermarkets[2],
//         brand: 'Haribo'
//     }
// ];
//

const App = StackNavigator({
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

export default App;
