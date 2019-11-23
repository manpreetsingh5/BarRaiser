import React, { Fragment, Component } from 'react';

import Row from 'react-bootstrap/Row';
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';

import {AiOutlineInbox} from 'react-icons/ai'

class Empty extends Component {

    render() {
        return (
            <Fragment>
                <AiOutlineInbox size={48}/>
            </Fragment>
        );
    }
}

export default Empty;
