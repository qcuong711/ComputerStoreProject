package com.qcuong.common.entity;

public enum OrderStatus {
	
	NEW {
		@Override
		public String defaultDescription() {
			return "Order has been created by customer";
		}
	},
	
	PROCESSING {
		@Override
		public String defaultDescription() {
			return "Order is being process";
		}
	}, 
	
	CANCELLED {
		@Override
		public String defaultDescription() {
			return "Order has been cancelled";
		}
	}, 
	
	SHIPPING {
		@Override
		public String defaultDescription() {
			return "Order is on the way";
		}
	}, 
	
	DELIVERED {
		@Override
		public String defaultDescription() {
			return "Order has been shipped";
		}
	};
	
	public abstract String defaultDescription(); 
	
}
