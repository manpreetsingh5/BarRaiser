import React, { Fragment, Component } from 'react';

import Image from 'react-bootstrap/Image';
import Button from 'react-bootstrap/Button'

class DrinkList extends Component {
    render() {
        let drinks = this.props.drinks;
        let cohortId = this.props.id;
        console.log(drinks)
        let list = []
        drinks.forEach((el, index) => {
            list.push(                                        
                <tr key={el.drink.id}>
                    <td>{index}</td>
                    <td>{el.drink.name}</td>
                    <td><Image src={`data:image/png;base64,${el.file}`} fluid /></td>
                    <td>
                        <Button variant="primary" onClick={() => this.props.addDrink(el.drink.id, cohortId)}>
                            Add
                        </Button>
                    </td>
                </tr>
            )
        })

        return(
            <Fragment>
                {list}
            </Fragment>
        )
    }
}

export default DrinkList