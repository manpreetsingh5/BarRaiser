import React, { Fragment, Component } from 'react';

import {Button} from 'react-bootstrap';
import { FaTrashAlt } from 'react-icons/fa';

class TraineeList extends Component {
    render() {
        let trainees = this.props.trainees;
        let cohort_id = this.props.cohort_id;
        let list = []
        trainees.forEach((el, index) => {
            list.push(
                <tr key={el.email}>
                    <td>{index}</td>
                    <td>{el.first_name}</td>
                    <td>{el.last_name}</td>
                    <td>{el.email}</td>
                    <td>
                        <Button variant="dark"  onClick={() => this.props.deleteTrainee(cohort_id, el.id)}>
                            <FaTrashAlt/>
                        </Button>
                    </td>
                </tr>
            )
        })

        return (
            <Fragment>
                {list}
            </Fragment>
        )
    }
}

export default TraineeList