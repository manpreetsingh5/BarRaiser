import React, { Fragment, Component } from 'react';

class TraineeList extends Component {
    render() {
        let trainees = this.props.trainees;
        let list = []
        trainees.forEach((el, index) => {
            list.push(                                        
                <tr key={el.email}>
                    <td>{index}</td>
                    <td>{el.first_name}</td>
                    <td>{el.last_name}</td>
                    <td>{el.email}</td>
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

export default TraineeList