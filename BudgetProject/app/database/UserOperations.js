import * as firebase from 'firebase'

export default class UserOperations {
    constructor() {
        this.usersRef = firebase.database().ref('/Users');
    }

    add(newUser) {
        console.log("In add : ", newUser);
        var id = newUser.id;
        console.log("id : ", id);
        var email = newUser.email;
        console.log("email : ", email);
        var role = newUser.role;
        console.log("role : ", role);

        this.usersRef.child(newUser.id).set({
            id: id,
            email: email,
            role: role
        });
    }


    makeAdmin(user) {
        console.log("In update : ", user);
        this.usersRef.child(user.id).update({
            id: user.id,
            name: user.name,
            role: "ADMIN"
        });
    }

    getAll() {
        console.log("In get all : ")
        let items = [];
        this.usersRef.on('value', (snapshot) => {
            snapshot.forEach((child) => {
                // console.log("item: ", child.val());
                items.push(child.val());
            });
        });
        return items;
    }
}
