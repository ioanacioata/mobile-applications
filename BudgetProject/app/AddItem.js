import React from 'react';
import {
    View,
    TextInput,
    Button,
    Text,
    Picker
} from 'react-native';

export default class AddItemScreen extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            id: 0,
            name: "",
            price: 0.0,
            supermarket: "",
            brand: ""
        };

    }

    ok() {
        var item = this.state;
        for (var i = 0; i < global.products.length; i++) {
            if (global.products[i].id === item.id) {
                global.products[i] = item;
            }
        }

        this.props.navigation.navigate("Home");
        //To do : navigate back and refresh the main page
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
                    {global.supermarkets.map((t, i) => {
                        return <Picker.Item label={t.name} value={t.name} key={t.name}/>
                    })}
                </Picker>
                <Text>Brand:</Text>
                <TextInput onChangeText={(brand) => this.setState({brand})} value={this.state.brand}/>

                <Button title="ok" onPress={() => this.ok()}/>
            </View>
        );
    }

}