import { Tenant } from "@/types/tenant";
import { createSlice } from "@reduxjs/toolkit";
import { RootState } from "../../store";
import User from "@/types/user";



interface UserState {

    userState?: User
}


// Initial state
const initialState: UserState = {};

// Actual Slice
export const userSlice = createSlice({
    name: "user",
    initialState,
    reducers: {
        // Action  
        setUser(state, action) {
            state.userState = action.payload;
        },

        setUserProfile(state, action) {

            if (state.userState) {
                state.userState.profile = action.payload;
            }
        },
    },

})


export const { setUser, setUserProfile } = userSlice.actions;
export const getUser = (state: RootState) => state.user.userState;
export default userSlice.reducer;
