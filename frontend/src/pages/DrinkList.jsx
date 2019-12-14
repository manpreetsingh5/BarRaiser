import React, { Fragment, Component } from 'react';

import style from '../style/Drinks.module.css';
import Image from 'react-bootstrap/Image';
import Button from 'react-bootstrap/Button'
import {Card, Row, Col} from 'react-bootstrap';

class DrinkList extends Component {
    render() {
        let drinks = this.props.drinks;
        let cohortId = this.props.id;
        console.log(drinks)
        let list = []
        drinks.forEach((el, index) => {
            list.push(                                        
                <Col key={el.drink.id} sm={3}>
                        <Card className={style.card}>
                            <Image src={`data:image/png;base64,${el.file}`} fluid />
                            <div className={style.cardContentDiv}>
                                <h5>{el.drink.name[0].toUpperCase() + el.drink.name.slice(1)}</h5>
                            </div>

                            <Button variant="primary" onClick={() => this.props.addDrink(el.drink.id, cohortId)}>
                                Add
                            </Button>
                        </Card>
                    </Col>
            )
        })

        return(
            <Fragment>
                <Row className="my-4">
                    {list}
                </Row>
            </Fragment>
        )
    }
}

export default DrinkList