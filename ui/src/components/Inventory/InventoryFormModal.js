import Button from '@material-ui/core/Button'
import Dialog from '@material-ui/core/Dialog'
import DialogActions from '@material-ui/core/DialogActions'
import DialogContent from '@material-ui/core/DialogContent'
import DialogTitle from '@material-ui/core/DialogTitle'
import Grid from '@material-ui/core/Grid'
import React from 'react'
import TextField from '../Form/TextField'
import { Field, Form, Formik } from 'formik'
import { MenuItem, Checkbox } from "@material-ui/core"
import MeasurementUnits from '../../constants/units'
//import { moment } from "moment"

class InventoryFormModal extends React.Component {

  render() {

    const {
      formName,
      handleDialog,
      handleInventory,
      title,
      initialValues,
      products,
    } = this.props



   const validate = values => {
            const errors = {};

            if (!values.name) {
                errors.name = "Inventory name is required."
            }
            if (!values.productType) {
                errors.productType = "Product type is required."
            }

            if (!values.unitOfMeasurement) {
                errors.unitOfMeasurement = "Unit of measurement is required."
            }

            return errors;
        
   }

    return (
      <Dialog
        open={this.props.isDialogOpen}
        maxWidth='sm'
        fullWidth={true}
        onClose={() => { handleDialog(false) }}
      >
        <Formik
          initialValues={initialValues}
          onSubmit={values => {
            values.bestBeforeDate = values.bestBeforeDate + "T20:06:04.319Z"
            handleInventory(values)
            handleDialog(true)
            console.log("IB values on submit "+ JSON.stringify(values))
          }}
          validate = {validate}
          >
          {helpers =>
            <Form
              //noValidate
              autoComplete='off'
              id={formName}
            >
              <DialogTitle id='alert-dialog-title'>
                {`${title} Inventory`}
              </DialogTitle>
              <DialogContent>
                <Grid container spacing={2}>
                  <Grid item xs={12} sm={12}>
                    <Field   /*Inventory Name field */
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='name'
                      //id='name'
                      label='Name'
                      component={TextField}
                      required={true}
                    />
                  </Grid>


                  <Grid item xs={12} sm={12}>
                    <Field   /*Product Type field */
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='productType'
                      label='Product Type'
                      required={true}
                      component={TextField} select
					            >
                      {products.map(product => {
                      return(<MenuItem value={product.name} key={product.id}>{product.name}</MenuItem>)
                      })}
                    </Field>
                  </Grid>

                  <Grid item xs={12} sm={12}>
                    <Field  /* Description Text field input. Optional. Initial value ‘’ */
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      //id='description'
                      name='description'
                      label='Description'
                      component={TextField}
                      //defaultValue=''
                    />
                  </Grid>

                  <Grid item xs={6} sm={6}>
                    <Field   /* Average Price. Number Text Field input. Optional. Initial value 0  */
                      custom={{ variant: 'outlined', fullWidth: false, }}
                      //id='averagePrice'
                      name='averagePrice'
                      label='Average Price'
                      component={TextField}
                      type='number'
                      //defaultValue = '0'
                    />
                  </Grid>

                  <Grid item xs={6} sm={6}>
                    <Field   /* Amount. Number Text Field input. Optional. Initial value 0 */
                      custom={{ variant: 'outlined', fullWidth: false, }}
                      //id='amount'
                      name='amount'
                      label='Amount'
                      component={TextField}
                      type='number'
                      //defaultValue = '0'
                    />
                  </Grid>

                  <Grid item xs={12} sm={12}>
                    <Field
                      custom={{ variant: 'outlined', fullWidth: true, }}
                      name='unitOfMeasurement'
                      label='Unit of Measurement'
                      required
                      component={TextField} select
                    >
                      {Object.keys(MeasurementUnits).map(unit => {
                      return(<MenuItem value={unit} key={MeasurementUnits[unit].name}>{unit}</MenuItem>)
                      })}
                    </Field>
                  </Grid>

                  <Grid item xs={6} sm={6}>
                    <Field    /* Best before date. Date Text Field input. Optional. Initial value today.  */
                      InputLabelProps={{ shrink: true }}
                      custom={{ variant: 'outlined', fullWidth: false, }}
                      id='bestBeforeDate'
                      name='bestBeforeDate'
                      label='Best Before Date'
                      component={TextField}
                      type='date'
                    />
                  </Grid>

                  <Grid item xs={6} sm={6}>
                    <Checkbox   /* Never expires. Checkbox input. Optional. Initial value false. */
                      custom={{ variant: 'outlined', fullWidth: false, }}
                      name='neverExpires'
                      label='Never Expires'
                    />Never Expires
                  </Grid>

                </Grid>
              </DialogContent>
              <DialogActions>
                <Button onClick={() => { handleDialog(false) }} color='secondary'>Cancel</Button>
                <Button
                  disableElevation
                  variant='contained'
                  type='submit'
                  form={formName}
                  color='secondary'
                  disabled={!helpers.dirty}>
                  Save
                </Button>
              </DialogActions>
            </Form>
          }
        </Formik>
      </Dialog>
    )

}

}

export default InventoryFormModal
