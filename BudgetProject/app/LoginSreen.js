import * as React from "react";
import firebase from 'firebase';
import {Button, StyleSheet, Text, TextInput, View} from "react-native";


export default class LoginScreen extends React.Component {
    constructor() {
        super();
        console.log("in constructor login screen");
        this.email = 'admin@budgetapp';
        this.password = 'adminapp';
    }

    render() {
        console.log("in render login screen");
        const {navigate} = this.props.navigation;
        return (
            <View>
                <Text>LOGIN</Text>
                <TextInput
                    style={{width: "80%", borderWidth: 1, backgroundColor: 'white'}}
                    onChangeText={(email) => this.email = email}
                    placeholder={"Email"}
                />

                <TextInput
                    style={{width: "80%", borderWidth: 1, backgroundColor: 'white'}}
                    onChangeText={(password) => this.password = password}
                    secureTextEntry={true}
                    placeholder={"Password"}
                />

                <Button
                    title="Login"
                    onPress={() => {
                        firebase.auth().signInWithEmailAndPassword(this.email, this.password)
                            .then(function () {
                                    alert("Welcome " + firebase.auth().currentUser.email + "!");
                                    navigate('Home');
                                }
                            ).catch(function (error) {
                            alert(error.code);
                            alert(error.message);
                        });

                    }}
                />

            </View>
        )
    }

}

