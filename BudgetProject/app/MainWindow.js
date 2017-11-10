import React from 'react';
import {
    Text,
    View,
    ListView,
    StyleSheet,
    TouchableOpacity
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
            <View>
                <Text>Products</Text>
                <ListView dataSource={this.state.productDataSource} renderRow={this.renderRow.bind(this)}/>
            </View>

        )
    }
};


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





