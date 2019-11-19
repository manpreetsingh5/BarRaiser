import React, { Fragment, Component } from 'react';
import style from '../style/Help.module.css';

import Row from 'react-bootstrap/Row'


class Help extends Component {
    render() {
        return (
            <Fragment>
                <Row className={style.container}>
                    <div className={style.titleDiv}>
                        <h3>Help</h3>
                    </div>
                </Row>
            </Fragment>
        );
    }
}

export default Help;
