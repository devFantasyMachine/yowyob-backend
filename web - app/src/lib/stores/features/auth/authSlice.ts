import { Tenant } from "@/types/tenant";
import { createSlice } from "@reduxjs/toolkit";
import { RootState } from "../../store";
import User from "@/types/user";



interface AuthState {
 
    isLogged: boolean
}


// Initial state
const initialState: AuthState = {
    isLogged: false
};

// Actual Slice
export const authSlice = createSlice({
    name: "auth",
    initialState,
    reducers: {
        // Action  
        setIsLogged(state, action) {
            state.isLogged = action.payload;
        },
    },

})


export const { setIsLogged } = authSlice.actions;
export const getAuthState = (state: RootState) => state.auth;
export default authSlice.reducer;

