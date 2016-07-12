package com.boqii.plant.data;/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import com.boqii.plant.data.Login.model.User;

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
public class FakeUser {

    public static User getFakeUser() {
        User user = new User();
        user.setAlias("Test");
        user.setUid("Test_001");
        return user;
    }

}
