import React from 'react';
import {
    Text,
    View,
    ListView,
    FlatList,
    StyleSheet,
    TouchableOpacity,
    Button, RefreshControl
} from 'react-native';

import {Pie} from 'react-native-pathjs-charts';
import ProductOperations from "./database/ProductOperations";
import TouchableItem from "../node_modules/react-navigation/lib-rn/views/TouchableItem";

export default class HomeScreen extends React.Component {

    constructor(props) {
        super(props);
        this.refresh = this.refresh.bind(this);

        this.productOperations = new ProductOperations();
        this.productList = [];
        this.state = {
            // dataSource: dataSource.cloneWithRows(global.products)
            refreshing: false,
        };


        this.data = [];
        this.getChartData();
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
        this.data.push({"name": "Profi Marasti", "products": 50});
        this.data.push({"name": "Auchan Iulius Mall", "products": 30});
        this.data.push({"name": "Kaufland Gheorgheni", "products": 100});
        this.data.push({"name": "Carefour Vivo", "products": 100});
    }

    refresh() {
        this.setState({refreshing: true});
        this.productList = this.productOperations.getAll();
        this.setState({refreshing: false});
    }

    componentWillMount() {
        this.refresh();
    }


    edit(item) {
        this.props.navigation.navigate("SeeItem", {refreshFunction: this.refresh.bind(this), item: item});
    }

    add() {
        this.props.navigation.navigate("AddItem", {refreshFunction: this.refresh.bind(this)});
    }

    logoutUser() {
        this.props.navigation.navigate("LoginScreen", {refreshFunction: this.refresh.bind(this)});
    }

    render() {
        const {navigate} = this.props.navigation;
        if (this.state.refreshing === true) {
            return (
                <View style={styles.container}>
                    <Text>Products are loading...</Text>
                </View>
            );
        } else
            return (
                <View style={{flex: 1, padding: 10, justifyContent: 'space-between'}}>
                    <Button title="Add" onPress={() => this.add()}/>
                    <Text>Products</Text>

                    <FlatList
                        data={this.productList}
                        extraData={this.productList}
                        refreshControl={
                            <RefreshControl
                                refreshing={this.state.refreshing}
                                onRefresh={this.refresh}
                            />
                        }

                        keyExtractor={(item, index) => index}
                        renderItem={({item}) =>
                            <View style={styles.listItemContainer}>
                                <TouchableItem onPress={() => this.edit(item)}>
                                    <View style={styles.container}>
                                        <Text style={styles.headline}>{item.name}</Text>
                                    </View>
                                </TouchableItem>
                            </View>

                        }
                    />

                    {/*<Pie*/}
                        {/*data={this.data}*/}
                        {/*options={this.options}*/}
                        {/*accessorKey="products"/>*/}

                    <Button title="Logout" onPress={() => this.logoutUser()}/>
                </View>
            );
    }

}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 12,
        backgroundColor: '#f4f4f4',
    },
    listItemContainer: {
        flex: 1,
        margin: 2
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






