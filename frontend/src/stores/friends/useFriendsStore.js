import { ref } from 'vue';
import axios from "axios";
import { defineStore } from 'pinia';
// import { userData } from "@/static/userData";

export const useFriendsStore = defineStore('friendsStore', () => {
    const friends = ref([]);

    const getUserList = async () => {
        try {
            const response = await axios.get('/api/search/alluser');
            friends.value = response.data;
        } catch (error) {
            console.error('Error fetching friends:', error);
        }
    };

    const getFriendsList = async ({workspaceId, userId, name }) => {
        try {
            const response = await axios.get(`/api/friends/search?workspaceId=${workspaceId}&id=${userId}&name=${name}`);
            friends.value = response.data;
        } catch (error) {
            console.error('Error fetching friends:', error);
        }
    }

    return {
        friends,
        getUserList,
        getFriendsList
    };
});