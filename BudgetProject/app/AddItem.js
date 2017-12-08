import React from 'react';
import {
    View,
    TextInput,
    Button,
    Text,
    Picker
} from 'react-native';
import StorageHelper from "./storage/StorageHelper";

export default class AddItemScreen extends React.Component {

    constructor(props) {
        super(props);
        this.storageHelper = new StorageHelper();
        this.state = {
            id: 0,
            name: "",
            price: 0.0,
            supermarket: "",
            brand: ""
        };
    }

    ok() {
        // var found=0;
        // var item = this.state;
        // for (var i = 0; i < global.products.length; i++) {
        //     if (global.products[i].id === item.id) {
        //         alert("Can't add this!");
        //         found =1;
        //     }
        // }
        // if(found===1){
        //     item.id=global.products[global.products.length-1].id+1;
        //     global.products.push(item);
        // }
        if (global.products.length > 0) {
            var id = global.products[global.products.length - 1].id + 1;
            this.state.id = id;
        }
        else {
            var id = 1;
        }
        this.state.id = id;
        var elem = {
            id: this.state.id,
            name: this.state.name,
            brand: this.state.brand,
            supermarket: this.state.supermarket,
            price: this.state.price
        };
        global.products.push(elem);
        this.storageHelper.addItem(elem);
        // console.error(this.props.navigation.params);

        this.props.navigation.state.params.refreshFunction();
        this.props.navigation.goBack();

    }

    render() {
        return (
            <View style={{flex: 1, padding: 10, justifyContent: 'space-between'}}>
                <Text>Name:</Text>
                <TextInput onChangeText={(name) => this.setState({name})} value={this.state.name}/>
                <Text>Price:</Text>
                <TextInput keyboardType='numeric' onChangeText={(price) => this.setState({price})}
                           value={this.state.price.toString()}/>
                <Text>Supermarket:</Text>
                <Picker
                    selectedValue={this.state.supermarket}
                    onValueChange={(itemValue, itemIndex) => this.setState({supermarket: itemValue})}>
                    <Picker.Item label={"nume1"} value={"nume1"} key={"nume1"}/>
                    <Picker.Item label={"nume2"} value={"nume2"} key={"nume2"}/>
                    <Picker.Item label={"nume3"} value={"nume3"} key={"nume3"}/>
                    {/*{global.supermarkets.map((t, i) => {*/}
                    {/*return <Picker.Item label={t.name} value={t.name} key={t.name}/>*/}
                    {/*})}*/}
                </Picker>
                <Text>Brand:</Text>
                <TextInput onChangeText={(brand) => this.setState({brand})} value={this.state.brand}/>

                <Button title="ok" onPress={() => this.ok()}/>
            </View>
        );
    }

}