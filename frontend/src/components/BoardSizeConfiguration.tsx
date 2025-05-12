import React, { useState, useEffect, useRef } from "react";
import {
  Box,
  Button,
  IconButton,
  Pagination, Select, MenuItem, SelectChangeEvent
} from "@mui/material";
import {
  Edit
} from "@mui/icons-material";
import { CellProps, CellValue, Column, useTable } from 'react-table';
import { useNavigate } from "react-router-dom";
import { ChangeEvent, useMemo } from "react";
import './styles.css';

export interface BoardSizeConfiguration {
  id?: any | null,
  value: number,
  label: string,
  createdAt: string,
  updatedAt: string,
}

const BoardSizeConfiguration: React.FC = () => {

  const [boardSizeConfiguration, setBoardSizeConfiguration] = useState<Array<BoardSizeConfiguration>>([]);

  const [page, setPage] = useState<number>(1);
  const [count, setCount] = useState<number>(0);
  const [pageSize, setPageSize] = useState<number>(3);

  const pageSizes = [3, 6, 9];

  const boarSizeRef = useRef<Array<BoardSizeConfiguration>>([]);
  let navigate = useNavigate();

  boarSizeRef.current = boardSizeConfiguration;

  const token = sessionStorage.getItem('token'); // Retrieve token from storage

  const fetchData = async () => {
    try {
      const response = await fetch(`http://localhost:8080/tictactoe_game_service/admin/board_size_configuration?page=${encodeURIComponent(page)}&pageSize=${encodeURIComponent(pageSize)}`, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${token}`, // Include the authorization header
          'Content-Type': 'application/json'
        },
      });

      console.log("Fetch Data Success:", response.ok);

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const json = await response.json();
        setBoardSizeConfiguration(json.data);
        setCount(json.paging.page);
    } catch (error) {
      console.error("Fetching error:", error);
      // Handle the error appropriately, e.g., display an error message
    }
  };

  

  // Fetch leaderboard data
  useEffect(() => {
    if (token) {
      fetchData();
    } else {
      // Handle the case where there is no token, e.g., redirect to login
      console.warn('No token available, cannot fetch data.');
    }
  }, [page, pageSize]);

  const handlePageChange = (event: ChangeEvent<unknown>, page: number) => {
    setPage(page);
  };

  const handlePageSizeChange = ({ target }: SelectChangeEvent<number>) => {
    setPageSize(target.value as number);
    setPage(1);
  };

  const refreshList = () => {
    fetchData();
  };

  const openBoardSize = (rowIndex: number) => {
    const id = boarSizeRef.current[rowIndex].id;
    navigate('/admin/board_size/' + id)
  };

  const adminBoardSize = () => {
    navigate('/admin/board_size/add');
  };

  const columns: Column<BoardSizeConfiguration>[] = useMemo(() => [
    {
      Header: 'ID',
      accessor: 'id' as keyof BoardSizeConfiguration,
    },
    {
      Header: 'Value',
      accessor: 'value' as keyof BoardSizeConfiguration,
    },
    {
      Header: 'Label',
      accessor: 'label' as keyof BoardSizeConfiguration,
    },
    {
      Header: 'Created At',
      accessor: 'createdAt' as keyof BoardSizeConfiguration,
    },
    {
      Header: 'Updated At',
      accessor: 'updatedAt' as keyof BoardSizeConfiguration,
    },
    {
      Header: 'Actions',
      accessor: 'actions' as CellValue,
      Cell: ({ row }: CellProps<BoardSizeConfiguration>): JSX.Element => {
        const rowIdx = row.id;
        return (
        <IconButton
            color="inherit"
            edge="start"
            onClick={() => openBoardSize(parseInt(rowIdx))}
          >
            <Edit />
          </IconButton>
        )
      }
    }
    // eslint-disable-next-line
  ], [])

  const {
    getTableProps,
    getTableBodyProps,
    prepareRow,
    headerGroups,
    rows,
  } = useTable<BoardSizeConfiguration>({ columns, data: boardSizeConfiguration })

  const findByTitle = () => {
    setPage(1);
    fetchData();
  };

  return (
    <Box
    sx={{
      p: 4,
      maxWidth: 1300,
      mx: "auto",
      textAlign: "center",
      fontFamily: "Poppins, sans-serif",
    }}
        >
    <div className="list row container mt-3">

      <Button
          variant="contained"
          color="primary"
          type="submit"
          onClick={adminBoardSize}
          fullWidth
          sx={{
            mt: 2,
            fontFamily: "Poppins",
            fontWeight: "bold",
            py: 1.5,
            marginBottom: 1
          }}
        >
    Add Board Size
    </Button>

      <div className="col-md-12 list">

        {"Items per Page: "}
        <Select
          onChange={handlePageSizeChange}
          value={pageSize}
        >
          {
            pageSizes.map((size) => (
              <MenuItem key={size} value={size}>
                {size}
              </MenuItem>
            ))
          }
        </Select>

        <table
          className="table table-striped table-bordered table-hover mt-3"
          {...getTableProps()}
        >
          <thead>
            {
              headerGroups.map(headerGroup => (
                <tr {...headerGroup.getHeaderGroupProps()}>
                  {
                    headerGroup.headers.map((column) => (
                      <th {...column.getHeaderProps()}>
                        {column.render("Header")}
                      </th>
                    ))
                  }
                </tr>
              ))
            }
          </thead>
          <tbody {...getTableBodyProps()}>
            {
              rows.map((row, i) => {
                prepareRow(row);
                return (
                  <tr {...row.getRowProps()}>
                    {
                      row.cells.map((cell) => {
                        return (
                          <td {...cell.getCellProps()}>{cell.render("Cell")}</td>
                        );
                      })
                    }
                  </tr>
                );
              })
            }
          </tbody>
        </table>

        <Pagination
          className="my-3"
          count={count}
          page={page}
          siblingCount={1}
          boundaryCount={1}
          variant="outlined"
          shape="rounded"
          onChange={handlePageChange}
        />

      </div>
    </div>

    </Box>
  )
};

export default BoardSizeConfiguration;
