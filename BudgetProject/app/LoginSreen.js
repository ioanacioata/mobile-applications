import * as React from "react";
import firebase from 'firebase';
import {Button, Text, TextInput, View} from "react-native";
import UserOperations from "./database/UserOperations";
import {UserApp} from "./model/UserApp";


export default class LoginScreen extends React.Component {
    constructor() {
        super();
        console.log("in constructor login screen");
        this.email = '';
        this.password = '';
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
                        console.log("LOGGING IN ... ",this.email);
                        firebase.auth().signInWithEmailAndPassword(this.email, this.password)
                            .then(function () {
                                    alert("Welcome back " + firebase.auth().currentUser.email + "!");
                                    navigate('Home');
                                }
                            ).catch(function (error) {
                            alert(error.code);
                            alert(error.message);
                        });

                    }}
                />
                <Button
                    title="Sign Up"
                    onPress={() => {
                        console.log("SIGNING UP ... ",this.email);
                        firebase.auth().createUserWithEmailAndPassword(this.email, this.password)
                            .then(function () {
                                alert("Welcome " + firebase.auth().currentUser.email + "!");
                                var userOp = new UserOperations();
                                var role = "USER";
                                if ( firebase.auth().currentUser.email === "admin@budgetapp.com") {
                                    role = "ADMIN";
                                }
                                var newUser = new UserApp(firebase.auth().currentUser.uid,firebase.auth().currentUser.email, role);
                                userOp.add(newUser);
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

