import React from 'react'
import Typography from "@mui/material/Typography";
import Box from "@mui/material/Box";
import { DataGrid } from "@mui/x-data-grid";

export default function DataGridComponent({title, columns, rows}) {
  return (
    <React.Fragment>
      <Typography component="h2" variant="h6" color="primary" gutterBottom>
        {title}
      </Typography>
      <Box sx={{ height: 400, width: "100%" }}>
        <DataGrid
          rows={rows}
          columns={columns}
          initialState={{
            pagination: {
              paginationModel: {
                pageSize: 5,
              },
            },
          }}
          pageSizeOptions={[5]}
          disableRowSelectionOnClick
        />
      </Box>
    </React.Fragment>
  )
}
