import React, { ChangeEvent, useState, useEffect } from "react";
import {
  TextField,
  Button,
  Box,
  Typography,
  useMediaQuery,
  CircularProgress,
} from "@mui/material";
import { useTheme } from "@mui/material/styles";
import { useForm } from "react-hook-form";
import { useNavigate, useParams } from "react-router-dom";
import { TurnTimeDurationConfiguration } from "./TurnTimeDurationConfiguration";

const UpdateTurnTimeDuration: React.FC = () => {
  const { register, handleSubmit } = useForm();
  const navigate = useNavigate();
  const theme = useTheme();

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [passwordVisible, setPasswordVisible] = useState(false);
  const [confirmPasswordVisible, setConfirmPasswordVisible] = useState(false);

  const [update, setUpdate] = useState(false);

  const initialTurnTimeDurationConfigurationState: TurnTimeDurationConfiguration = {
    id: null,
    value: 0,
    label: "",
    createdAt: "",
    updatedAt:""
};

const [currentTurnTimeConfiguration, setCurrentTurnTimeConfiguration] = useState<TurnTimeDurationConfiguration>(initialTurnTimeDurationConfigurationState);

  const { id } = useParams();
  const token = sessionStorage.getItem('token'); // Retrieve token from storage
  const fetchData = async () => {
      try {
        const response = await fetch('http://localhost:8080/tictactoe_game_service/admin/turn_time_duration_configuration/'+id, {
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
        setCurrentTurnTimeConfiguration(json);
      } catch (error) {
        console.error("Fetching error:", error);
        // Handle the error appropriately, e.g., display an error message
      }
    };
  
    const adminTurnTime = () => {
      // If game is over or board is empty, reset the board
      navigate("/admin/turn_time");
    };
  
    // Fetch leaderboard data
    useEffect(() => {
      if (token && id) {
        setUpdate(true);
        fetchData();
      } else {
        setUpdate(false);
      }
    }, [id, update]);

    
  const onSubmit = async (data: any) => {

    try {
      const response = await fetch('http://localhost:8080/tictactoe_game_service/admin/turn_time_duration_configuration', {
        method: 'POST',
        headers: {
          'Authorization': `Bearer ${token}`, // Include the authorization header
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          id: currentTurnTimeConfiguration.id,
          value: currentTurnTimeConfiguration.value,
          label: currentTurnTimeConfiguration.label
          }),
      });

      setLoading(true);
      setError("");

      console.log("Fetch Data Success:", response.ok);

      if (!response.ok) {
        setError("Application dealing with Unexpected Error. Please inform your application administrator");
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const json = await response.json();
      navigate("/admin/turn_time");
    } catch (error) {
      console.error("Fetching error:", error);
      // Handle the error appropriately, e.g., display an error message
    }finally {
      setLoading(false);
    }
  
  };

  const handleInputChange = ({ target }: ChangeEvent<HTMLInputElement>) => {
    setCurrentTurnTimeConfiguration({ ...currentTurnTimeConfiguration, [target.name]: target.value });
};

  return (
    <Box
      sx={{
        display: "flex",
        flexDirection: "column",
        flex: 1,
        justifyContent: "center",
        alignItems: "center",
        minHeight: "calc(100vh - 64px - 100px)",
        textAlign: "center",
      }}
    >
      <Box
        sx={{
          maxWidth: 400,
          p: 3,
          bgcolor: "background.paper",
          borderRadius: 2,
          boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.1)",
          textAlign: "center",
        }}
      >
        <Typography
          variant="h4"
          gutterBottom
          sx={{
            fontFamily: "Poppins",
            fontWeight: "bold",
            textAlign: "center",
          }}
        >
          {update ? "Update" : "Add"}
        </Typography>
        <Typography
          variant="body1"
          sx={{
            fontFamily: "Poppins",
            color: "text.secondary",
            mb: 3,
            textAlign: "center",
          }}
        >
          {update ? "Update Board Size" : "Add Board Size"}
        </Typography>
        <form onSubmit={handleSubmit(onSubmit)}>
          {update && (
            <TextField
            {...register("id")}
            fullWidth
            margin="normal"
            type="text"
            value={currentTurnTimeConfiguration.id}
            disabled
          
            sx={{
              "& .MuiInputBase-input": {
                fontFamily: "Poppins",
              },
              "& .MuiInputLabel-root": {
                fontFamily: "Poppins",
              },
            }}
          />
          )}
          
          <TextField
            {...register("value")}
            label="Value"
            fullWidth
            margin="normal"
            type="text"
            name="value"
            value={currentTurnTimeConfiguration.value}
            onChange={handleInputChange}
            required
          
            sx={{
              "& .MuiInputBase-input": {
                fontFamily: "Poppins",
              },
              "& .MuiInputLabel-root": {
                fontFamily: "Poppins",
              },
            }}
          />
          <TextField
           {...register("label")}
            label="Label"
            fullWidth
            margin="normal"
            type="text"
            name="label"
            value={currentTurnTimeConfiguration.label}
            onChange={handleInputChange}
            required
          
            sx={{
              "& .MuiInputBase-input": {
                fontFamily: "Poppins",
              },
              "& .MuiInputLabel-root": {
                fontFamily: "Poppins",
              },
            }}
          />
  
          
          <Button
            variant="contained"
            color="primary"
            type="submit"
            fullWidth
            disabled={loading}
            sx={{
              mt: 2,
              fontFamily: "Poppins",
              fontWeight: "bold",
              py: 1.5,
            }}
          >
            {/* {loading ? (
              <CircularProgress size={24} sx={{ color: "white" }} />
            ) : (
             "" //  {update ? "Update" : "Add"}
            )} */}
            {(() => {
              if (loading) {
                return (
                  <CircularProgress size={24} sx={{ color: "white" }} />
                )
              } else if (update) {
                return (
                  "Update"
                )
              } else {
                return (
                  "Add"
                )
              }
            })()}
          </Button>
          <Button
                  variant="contained"
                  color="primary"
                  fullWidth
                  onClick={adminTurnTime}
                  sx={{
                    mt: 2,
                    fontFamily: "Poppins",
                    fontWeight: "bold",
                    py: 1.5,
                  }}
                >
                  Back to Admin
                </Button>
        </form>
        {error && (
          <Typography
            variant="body2"
            color="error"
            sx={{
              mt: 3,
              fontFamily: "Poppins",
              textAlign: "center",
            }}
          >
            {error}
          </Typography>
        )}
      </Box>
    </Box>
  );
};

export default UpdateTurnTimeDuration;
