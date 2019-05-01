package com.boss.cuncis.bukatoko.data.model;

import com.google.gson.annotations.SerializedName;

public class User {

	@SerializedName("data")
	private Data data;

	@SerializedName("status")
	private Status status;

	public Data getData(){
		return data;
	}

	public Status getStatus(){
		return status;
	}

	public class Data {

		@SerializedName("is_admin")
		private int isAdmin;

		@SerializedName("api_token")
		private String apiToken;

		@SerializedName("name")
		private String name;

		@SerializedName("id")
		private int id;

		@SerializedName("email")
		private String email;

		@SerializedName("username")
		private String username;

		public int getIsAdmin() {
			return isAdmin;
		}

		public String getApiToken() {
			return apiToken;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}

		public String getEmail() {
			return email;
		}

		public String getUsername() {
			return username;
		}
	}

	public class Status{

		@SerializedName("code")
		private int code;

		@SerializedName("description")
		private String description;

		public int getCode(){
			return code;
		}

		public String getDescription(){
			return description;
		}
	}
}