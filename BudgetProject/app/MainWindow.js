import React from 'react';
import {
    Text,
    View,
    ListView,
    StyleSheet,
    TouchableOpacity,
    Button, AsyncStorage
} from 'react-native';

import {Pie} from 'react-native-pathjs-charts';
import StorageHelper from "./storage/StorageHelper";

var dataSource = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
export default class HomeScreen extends React.Component {

    constructor(props) {
        super(props);
        this.storageHelper = new StorageHelper();
        //AsyncStorage.setItem("productsList", JSON.stringify(["1", "2"]));
        //AsyncStorage.removeItem("productsList");
        // AsyncStorage.setItem("1", JSON.stringify({
        //         id: 1,
        //         name: 'Coca-Cola 0.5l',
        //         price: 2.5,
        //         supermarket: global.supermarkets[0],
        //         brand: 'Coca-Cola'
        //     }));
        this.storageHelper.initArray().then(() => {
            this.refresh()
        }, () => {
        });

        this.state = {
            dataSource: dataSource.cloneWithRows(global.products)
        };


        //for the chart
        //    this.data = [{"name":"name","population":10}, {"name":"name1","population":70}];

        // this.populateChartData();


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
        this.setState(prevState => {
            return Object.assign({}, prevState, {dataSource: dataSource.cloneWithRows(global.products)});

        });
    }

    populateChartData() {
        for (var i = 0; i < global.supermarkets.length; i++) {
            var name = global.supermarkets[i];
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
        this.props.navigation.navigate("SeeItem", {refreshFunction: this.refresh.bind(this), item:item});
    }

    add() {
        this.props.navigation.navigate("AddItem", {refreshFunction: this.refresh.bind(this)});
    }

    renderRow(item, sectionId, rowId, highlightRow) {
        return (
            <TouchableOpacity onPress={() => this.edit(item)}>
                <View style={styles.container}>
                    <Text style={styles.headline}>{item.name}</Text>
                </View>
            </TouchableOpacity>
        );
    }

    render() {
        return (
            <View style={{flex: 1, padding: 10, justifyContent: 'space-between'}}>
                <Text>Products</Text>
                <ListView dataSource={this.state.dataSource} renderRow={this.renderRow.bind(this)}/>
                <Button title="Add" onPress={() => this.add()}/>

                {/*<Pie*/}
                {/*data={this.data}*/}
                {/*options={this.options}*/}
                {/*accessorKey="population"/>*/}
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





