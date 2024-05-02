import * as React from "react";
import { useState, useEffect } from "react";
import Button from "@mui/material/Button";
import { getAllDoctorPendingRequest } from "../../services/AdminDashboardService";
import { useNavigate } from "react-router";
import DataGridComponent from "../../components/DataGridComponet";

export default function DoctorPendingRequest() {
  
  // state used to store doctor pending requests fetched from backend
  const [doctorPendingRequest, setDoctorPendingRequest] = useState([]);

  const navigate = useNavigate();

  // decide which fields to disply to admin
  const columns = [
    {
      field: "username",
      headerName: "Username",
      width: 250,
    },
    {
      field: "postalCode",
      headerName: "Postal Code",
      width: 250,
    },
    {
      field: "licenseNumber",
      headerName: "License Number",
      width: 250,
    },
    {
      field: "details",
      headerName: "Details",
      width: 90,
      renderCell: (params) => {
        return (
          <Button
            variant="contained"
            size="small"
            style={{ background: "black" }}
            onClick={() => navigate(`/admin/doctor-pending-request/${params.row.username}`)}
          >
            View
          </Button>
        );
      },
    },
  ];

  // fetch all pending requests of doctors.
  const fetchData = async () => {
    getAllDoctorPendingRequest()
      .then((res) => {
        // temp object to store doctor pending requests
        let data = [];

        for (let i = 0; i < res.doctorPendingRequests.length; i++) {
          data.push({
            id: i,
            doctor_id: res.doctorPendingRequests[i]["id"],
            postalCode: res.doctorPendingRequests[i]["postalCode"],
            licenseNumber: res.doctorPendingRequests[i]["licenseNumber"],
            username: res.doctorPendingRequests[i]["username"],
          });
        }
        setDoctorPendingRequest(data);
      })
      .catch((err) => {
        console.log(err);
      });
  };

  useEffect(() => {
    // call this function to fetch data
    fetchData();
  }, []);

  return (
    <DataGridComponent title="Doctor's Pending Registration" columns={columns} rows={doctorPendingRequest} />
  );
}
