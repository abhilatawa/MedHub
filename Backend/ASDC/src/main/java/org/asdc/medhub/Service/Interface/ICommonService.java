package org.asdc.medhub.Service.Interface;

import org.asdc.medhub.Utility.Model.DatabaseModels.Appointment;
import org.asdc.medhub.Utility.Model.DatabaseModels.Doctor;
import org.asdc.medhub.Utility.Model.DatabaseModels.Pharmacist;
import org.asdc.medhub.Utility.Model.ResponseModels.AppointmentDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.DoctorDetail;
import org.asdc.medhub.Utility.Model.ResponseModels.PharmacistDetail;

/**
 * Interface for common service
 */
public interface ICommonService {

    /**
     * Converts a Doctor entity to a DoctorDetail object.
     * @param doctor The Doctor entity to be converted.
     * @return The corresponding DoctorDetail object.
     */
    DoctorDetail convertDoctorToDoctorDetail(Doctor doctor);

    /**
     * Updates the Doctor entity using information from the DoctorDetail object.
     *
     * @param doctor        The Doctor entity to be updated.
     * @param doctorDetail  The DoctorDetail object containing updated doctor profile information.
     */
    Doctor updateDoctorFromDoctorDetailModel(Doctor doctor, DoctorDetail doctorDetail);

    /**
     * Converts Appointment to AppointmentDetail
     * @param appointment Appointment from db
     * @return AppointmentDetail
     */
    AppointmentDetail getAppointmentDetailModelFromAppointment(Appointment appointment);

    /**
     * Converts a Pharmacist object to a PharmacistDetail object for an individual pharmacist.
     *
     * @param pharmacist The Pharmacist object to be converted to PharmacistDetail.
     * @return A PharmacistDetail object containing the details of the pharmacist.
     */
    PharmacistDetail convertPharmacistToPharmacistDetail(Pharmacist pharmacist);
}
