import React from 'react';
import {
    Text,
    View,
    ListView,
    StyleSheet,
    TouchableOpacity,
    Button
} from 'react-native';

export default class HomeScreen extends React.Component {

    constructor() {
        super();
        const dataSource = new ListView.DataSource({rowHasChanged: (r1, r2) => r1 !== r2});
        this.state = {
            productDataSource: dataSource.cloneWithRows(products),
        };
    }

    edit(item) {
        this.props.navigation.navigate("SeeItem", item);
    }

    add() {
        this.props.navigation.navigate("AddItem");
    }

    renderRow(item, sectionId, rowId, highlightRow) {
        return (
            <TouchableOpacity onPress={() => this.edit(item)}>
                <View style={styles.container}>
                    <Text style={styles.headline}>{item.name}</Text>
                </View>
            </TouchableOpacity>
        )
    }

    render() {
        return (
            <View style={{flex:1, padding:10, justifyContent:'space-between'}}>
                <Text>Products</Text>
                <ListView dataSource={this.state.productDataSource} renderRow={this.renderRow.bind(this)}/>
                <Button title="Add"  onPress={() => this.add()}/>
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





