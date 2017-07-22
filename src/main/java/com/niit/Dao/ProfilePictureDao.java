package com.niit.Dao;

import com.niit.Model.ProfilePicture;

public interface ProfilePictureDao {
	void saveProfilePicture(ProfilePicture profilePicture);

	ProfilePicture getProfilePicture(String username);
}
