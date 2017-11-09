import React, {Component} from 'react';
import {AppRegistry, Text, View, ListView, StyleSheet} from 'react-native';

const products = [
    {
        name: 'Coca-Cola 0.5l',
        price: '2.5',
        supermarket: 'Auchan Iulis Mall',
        brand: 'Coca-Cola'
    },

    {
        name: 'Cutie servetele',
        price: '5.5',
        supermarket: 'Lidl',
        brand: 'Cien'
    },
    {
        name: 'Apa minerala 0.5l',
        price: '3.0',
        supermarket: 'Auchan Iulis Mall',
        brand: 'Aqua Carpatica'
    },
    {
        name: 'Ciocolata cu Oreo',
        price: '3.5',
        supermarket: 'Auchan Iulis Mall',
        brand: 'Milka'
    },
    {
        name: 'Ciocolata cu Capsuni',
        price: '3.5',
        supermarket: 'Auchan Iulis Mall',
        brand: 'Milka'
    },
    {
        name: 'Gummy Bears',
        price: '3.5',
        supermarket: 'Auchan Iulis Mall',
        brand: 'Haribo'
    },
    {
        name: 'Paine',
        price: '3.5',
        supermarket: 'Panemar',
        brand: 'Panemar'
    },
    {
        name: 'Paine',
        price: '3.5',
        supermarket: 'Panemar',
        brand: 'Panemar'
    }

];

export default class ListComponent extends Component {

    constructor() {
        super();
        const ds = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this.state = {
            productDataSource: ds.cloneWithRows(products),
        };
    }

    renderRow(product, sectionId, rowId, highlightRow) {
        return (
            <View style={styles.container}>
                <Text style={styles.headline}>{product.name}</Text>
            </View>
        )
    }

    render() {
        return (
            <ListView
                dataSource={this.state.productDataSource}
                renderRow={this.renderRow.bind(this)}
            />
        );
    }

}

const styles = StyleSheet.create({

    container: {
        flex: 1,
        padding: 25,
        backgroundColor: '#f4f4f4',
        marginBottom: 3
    },

    details: {
        //fontSize: 14,
        marginBottom: 8
    },
    headline: {
        fontSize: 20,
        fontWeight: 'bold',
        marginBottom: 8,
        flex: 1
    }

});

AppRegistry.registerComponent('ListComponent', () => ListComponent);
