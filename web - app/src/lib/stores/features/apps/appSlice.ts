import { createSlice } from "@reduxjs/toolkit";
import { RootState } from "../../store";
import { App } from "@/types/app";



interface AppState {

    currentApp?: App
}


// Initial state
const initialState: AppState = {currentApp: undefined};

// Actual Slice
export const appSlice = createSlice({
    name: "app",
    initialState,
    reducers: {
        // Action  
        setCurrentApp(state, action) {
            state.currentApp = action.payload;
        },
    },

})


export const { setCurrentApp } = appSlice.actions;
export const getCurrentApp = (state: RootState) => state.app.currentApp;
export default appSlice.reducer;
