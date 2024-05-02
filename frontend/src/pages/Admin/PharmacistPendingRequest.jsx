import * as React from "react";
import { useState, useEffect } from "react";
import Button from "@mui/material/Button";
import { useNavigate } from "react-router";
import { getAllPharmacistPendingRequest } from "../../services/AdminDashboardService";
import DataGridComponent from "../../components/DataGridComponet";

export default function PharmacistPendingRequest() {

  // state used to store pharmacist pending requests fetched from backend
  const [pharmacistPendingRequest, setPharmacistPendingRequest] = useState([]);

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
            onClick={() =>
              navigate(
                `/admin/pharmacist-pending-request/${params.row.username}`
              )
            }
          >
            View
          </Button>
        );
      },
    },
  ];

  // fetch all pending requests of pharmacists.
  const fetchData = async () => {
    getAllPharmacistPendingRequest()
      .then((res) => {
        // temp object to store pharmacist pending requests
        let data = [];

        for (let i = 0; i < res.pharmacistPendingRequests.length; i++) {
          data.push({
            id: i,
            pharmacist_id: res.pharmacistPendingRequests[i]["id"],
            postalCode: res.pharmacistPendingRequests[i]["postalCode"],
            licenseNumber: res.pharmacistPendingRequests[i]["licenseNumber"],
            username: res.pharmacistPendingRequests[i]["username"],
          });
        }
        setPharmacistPendingRequest(data);
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
    <DataGridComponent
      title="Phamacist's Pending Registration"
      columns={columns}
      rows={pharmacistPendingRequest}
    />
  );
}
