import React, { Fragment, Component } from 'react';
import style from '../style/Drinks.module.css';

import Row from 'react-bootstrap/Row'


class Drinks extends Component {
    render() {
        return (
            <Fragment>
                <Row className={style.container}>
                    <div className={style.titleDiv}>
                        <h3>Drinks</h3>
                    </div>
                </Row>
            </Fragment>
        );
    }
}

export default Drinks;
