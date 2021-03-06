import React from 'react';
import {
    Text,
    View,
    ListView,
    StyleSheet,
    TouchableOpacity,
    Button
} from 'react-native';

import {Pie} from 'react-native-pathjs-charts';
import StorageHelper from "./storage/StorageHelper";

var dataSource = new ListView.DataSource({
    rowHasChanged: (r1, r2) => {
        return r1.id !== r2.id;
    }
});

export default class HomeScreen extends React.Component {

    constructor(props) {
        super(props);
        this.storageHelper = new StorageHelper();

        this.storageHelper.initArray().then(
            () => {
                this.refresh();
            },
            () => {
            }
        );
        this.state = {
            dataSource: dataSource.cloneWithRows(global.products)
        };

        //for the chart
        this.data = [];
        this.populateChartData();
        this.options = {
            margin: {
                top: 5,
                left: 20,
                right: 20,
                bottom: 5
            },
            width: 300,
            height: 300,
            color: '#2980B9',
            r: 20,
            R: 140,
            legendPosition: 'topLeft',
            animate: {
                type: 'oneByOne',
                duration: 2000,
                fillTransition: 3
            },
            label: {
                fontFamily: 'Arial',
                fontSize: 10,
                fontWeight: true,
                color: '#ECF0F1'
            }
        };
    }

    refresh() {
        // console.error(global.products);
        this.setState(prevState => {
            return Object.assign({}, prevState, {dataSource: dataSource.cloneWithRows(global.products)})
        });


    }

    populateChartData() {
        for (var i = 0; i < global.supermarkets.length; i++) {
            var name = global.supermarkets[i].name;
            var count = 0;
            for (var j = 0; j < global.products.length; j++) {
                if (global.products[j].supermarket === name) {
                    count++;
                }
            }
            this.data.push({"name": name, "prod": count});
        }
    }

    edit(item) {
        this.props.navigation.navigate("SeeItem", {item: item, refreshFunction: this.refresh.bind(this)});
    }

    add() {
        this.props.navigation.navigate("AddItem", {refreshFunction: this.refresh.bind(this)});
    }

    renderRow(item) {
        return (
            <TouchableOpacity onPress={() => this.edit(item)}>
                <View>
                    <Text>{item.name}</Text>
                </View>
            </TouchableOpacity>
        );
    }

    render() {
        return (
            <View style={{flex: 1, padding: 10, justifyContent: 'space-between'}}>
                <Text>Products</Text>
                <ListView dataSource={this.state.dataSource} renderRow={this.renderRow.bind(this)} />
                <Button title="Add" onPress={() => this.add()}/>

                <Pie
                    data={this.data}
                    options={this.options}
                    accessorKey="prod"/>
            </View>
        )
    }
};


const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 12,
        backgroundColor: '#f4f4f4',
    },

    details: {
        marginBottom: 2
    },
    headline: {
        fontSize: 18,
        fontWeight: 'bold',
        marginBottom: 3,
        flex: 1
    }


});





