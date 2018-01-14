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
import ProductOperations from "./database/ProductOperations";

var dataSource = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
export default class HomeScreen extends React.Component {

    constructor(props) {
        super(props);
        // this.storageHelper = new StorageHelper();

        this.prooductOperations = new ProductOperations();
        // this.storageHelper.initArray().then(() => {
        //     this.refresh()
        // }, () => {
        // });
        // this.refresh();

        global.products=this.prooductOperations.getAll();
        this.state = {
            dataSource: dataSource.cloneWithRows(global.products)
        };


        this.data = [];
        this.getChartData();
        console.log("Char data: ",this.data)

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

    getChartData() {
        this.data.push({"name": "Type1", "prod": 50});
        this.data.push({"name": "Type2", "prod": 40});
        this.data.push({"name": "Type3", "prod": 10});
    }

    refresh() {
        this.setState(prevState => {
            return Object.assign({}, prevState, {dataSource: dataSource.cloneWithRows(this.prooductOperations.getAll())});
        });
    }


    edit(item) {
        this.props.navigation.navigate("SeeItem", {refreshFunction: this.refresh.bind(this), item: item});
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
                    {/*accessorKey="prod"/>*/}
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





