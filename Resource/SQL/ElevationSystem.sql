USE [ElevationSystem]
GO
/****** Object:  ForeignKey [FK_OderDetails_Clients]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_OderDetails_Clients]') AND parent_object_id = OBJECT_ID(N'[dbo].[Order]'))
ALTER TABLE [dbo].[Order] DROP CONSTRAINT [FK_OderDetails_Clients]
GO
/****** Object:  ForeignKey [FK_Maintenance_Bill]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Maintenance_Bill]') AND parent_object_id = OBJECT_ID(N'[dbo].[Maintenance]'))
ALTER TABLE [dbo].[Maintenance] DROP CONSTRAINT [FK_Maintenance_Bill]
GO
/****** Object:  ForeignKey [FK_Employee_Users]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Employee_Users]') AND parent_object_id = OBJECT_ID(N'[dbo].[Employee]'))
ALTER TABLE [dbo].[Employee] DROP CONSTRAINT [FK_Employee_Users]
GO
/****** Object:  ForeignKey [FK_Complaints_Clients]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Complaints_Clients]') AND parent_object_id = OBJECT_ID(N'[dbo].[Complaints]'))
ALTER TABLE [dbo].[Complaints] DROP CONSTRAINT [FK_Complaints_Clients]
GO
/****** Object:  ForeignKey [FK_Complaints_Employee]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Complaints_Employee]') AND parent_object_id = OBJECT_ID(N'[dbo].[Complaints]'))
ALTER TABLE [dbo].[Complaints] DROP CONSTRAINT [FK_Complaints_Employee]
GO
/****** Object:  Table [dbo].[Complaints]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Complaints_Clients]') AND parent_object_id = OBJECT_ID(N'[dbo].[Complaints]'))
ALTER TABLE [dbo].[Complaints] DROP CONSTRAINT [FK_Complaints_Clients]
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Complaints_Employee]') AND parent_object_id = OBJECT_ID(N'[dbo].[Complaints]'))
ALTER TABLE [dbo].[Complaints] DROP CONSTRAINT [FK_Complaints_Employee]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Complaints]') AND type in (N'U'))
DROP TABLE [dbo].[Complaints]
GO
/****** Object:  Table [dbo].[Employee]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Employee_Users]') AND parent_object_id = OBJECT_ID(N'[dbo].[Employee]'))
ALTER TABLE [dbo].[Employee] DROP CONSTRAINT [FK_Employee_Users]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Employee]') AND type in (N'U'))
DROP TABLE [dbo].[Employee]
GO
/****** Object:  Table [dbo].[Maintenance]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Maintenance_Bill]') AND parent_object_id = OBJECT_ID(N'[dbo].[Maintenance]'))
ALTER TABLE [dbo].[Maintenance] DROP CONSTRAINT [FK_Maintenance_Bill]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Maintenance]') AND type in (N'U'))
DROP TABLE [dbo].[Maintenance]
GO
/****** Object:  Table [dbo].[Order]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_OderDetails_Clients]') AND parent_object_id = OBJECT_ID(N'[dbo].[Order]'))
ALTER TABLE [dbo].[Order] DROP CONSTRAINT [FK_OderDetails_Clients]
GO
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Order]') AND type in (N'U'))
DROP TABLE [dbo].[Order]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Users]') AND type in (N'U'))
DROP TABLE [dbo].[Users]
GO
/****** Object:  Table [dbo].[Bill]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Bill]') AND type in (N'U'))
DROP TABLE [dbo].[Bill]
GO
/****** Object:  Table [dbo].[Clients]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Clients]') AND type in (N'U'))
DROP TABLE [dbo].[Clients]
GO
/****** Object:  Table [dbo].[Elevator]    Script Date: 11/29/2013 15:48:48 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Elevator]') AND type in (N'U'))
DROP TABLE [dbo].[Elevator]
GO
/****** Object:  Table [dbo].[Elevator]    Script Date: 11/29/2013 15:48:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Elevator]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Elevator](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NULL,
	[image] [nvarchar](255) NULL,
	[type] [nvarchar](255) NULL,
	[price] [float] NULL,
	[weight] [float] NULL,
	[speed] [float] NULL,
	[madein] [nvarchar](255) NULL,
	[warranty] [int] NULL,
 CONSTRAINT [PK_Elevator] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET IDENTITY_INSERT [dbo].[Elevator] ON
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1004, N'THYSSENKRUPP 450Kg', N'1385379847294.png', N'home elevator', 23137, 450, 60, N'Germany', 1)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1005, N'ACE-F200-2', N'1385380151409.png', N'Heavy duty elevator', 4740, 200, 15, N'Venture', 2)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1006, N'Mitsubishi ACE-P-450-7CO', N'1385380246400.png', N'home elevator', 9485, 450, 45, N'Venture', 3)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1007, N'Mitsubishi P600KG', N'1385380360729.png', N'home elevator', 19273, 600, 60, N'Venture', 2)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1008, N'PMTT-1000KG', N'1385380461702.png', N'heavy duty elevator', 9873, 1000, 25, N'Viet Nam', 3)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1009, N'Mitshubishi Hanec 05', N'1385380664655.png', N'home elevator', 3982, 450, 60, N'Venture', 1)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1010, N'HANEC CO15MPM', N'1385380735576.png', N'food elevator', 1293, 180, 60, N'Germany', 2)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1011, N'Sakura M68', N'1385380793258.png', N'home elevator', 5493, 200, 30, N'Japan', 3)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1012, N'NT300', N'1385380849608.png', N'heavy duty elevator', 12988, 300, 20, N'Viet Nam', 3)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1013, N'VINALIFT ELEVATOR DW200-15MPM', N'1385381067673.jpg', N'Food elevator', 3500, 200, 15, N'Venture', 2)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1014, N'TH 630kg', N'1385381152248.jpg', N'Home elevator', 19000, 600, 60, N'Venture', 3)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1015, N'TH 450kg', N'1385381211326.jpg', N'home elevator', 1999, 450, 60, N'Viet Nam', 1)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1016, N'NT50', N'1385381264830.jpg', N'food elevator', 1209, 90, 35, N'Viet Nam', 1)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1017, N'MITSUBISHI 1850x1650', N'1385381462326.jpg', N'home elevator', 17290, 300, 60, N'Germany', 3)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1018, N'Montanari 300 KG', N'1385381602968.png', N'home elevator', 14500, 300, 60, N'Venture', 2)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1019, N'TH 550kg', N'1385385637517.jpg', N'home elevator', 14600, 550, 90, N'Germany', 2)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1020, N'EF-50kg/4S15-SEP2', N'1385385690629.jpg', N'food elevator', 4300, 100, 15, N'Viet Nam', 1)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1021, N'QK-1', N'1385385743361.JPG', N'food elevator', 1300, 90, 20, N'Viet Nam', 1)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1022, N'NME 300 Kg', N'1385385821024.jpg', N'home elevator', 17050, 300, 60, N'Viet Nam', 2)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1023, N'Mitsubishi ACE-P-600-7CO', N'1385385873420.jpg', N'home elevator', 19999, 750, 75, N'Japan', 3)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1024, N'ACE-P-900-3CO', N'1385385916046.jpg', N'home elevator', 18500, 900, 90, N'Japan', 3)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1025, N'TH 900kg', N'1385385966645.JPG', N'home elevator', 19700, 900, 90, N'Korea', 2)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1026, N'hitachi 10000Kg', N'1385386072255.jpg', N'escalators', 21000, 10000, 15, N'China', 3)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1028, N'TH 750kg', N'1385386210353.JPG', N'home elevator', 17500, 750, 90, N'China', 3)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1029, N'TAE 5kw', N'1385386253070.jpg', N'home elevator', 9800, 550, 60, N'Japan', 1)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1030, N'Mitsubishi ACE-P-600-8CO', N'1385386296119.jpg', N'home elevator', 19800, 600, 60, N'Viet Nam', 1)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1031, N'MITSUBISHI 1850x1650', N'1385386375213.jpg', N'home elevator', 7900, 350, 60, N'Viet Nam', 2)
INSERT [dbo].[Elevator] ([id], [name], [image], [type], [price], [weight], [speed], [madein], [warranty]) VALUES (1032, N'freight lift', N'1385386168063.jpg', N'heavy duty elevator', 5500, 1000, 15, N'Korea', 2)
SET IDENTITY_INSERT [dbo].[Elevator] OFF
/****** Object:  Table [dbo].[Clients]    Script Date: 11/29/2013 15:48:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Clients]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Clients](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](255) NULL,
	[company] [nvarchar](255) NULL,
	[address] [nvarchar](255) NULL,
	[email] [nvarchar](255) NULL,
	[mobile] [nvarchar](255) NULL,
 CONSTRAINT [PK_Clients] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET IDENTITY_INSERT [dbo].[Clients] ON
INSERT [dbo].[Clients] ([id], [name], [company], [address], [email], [mobile]) VALUES (7, N'Phuong', N'FPT', N'Cau Giay', N'phuong@fpt.com', N'09123817412')
INSERT [dbo].[Clients] ([id], [name], [company], [address], [email], [mobile]) VALUES (8, N'Toan', N'FPT', N'Cau Giay', N'toan@fpt.com', N'9285245245')
INSERT [dbo].[Clients] ([id], [name], [company], [address], [email], [mobile]) VALUES (9, N'Tung1', N'FPT', N'Cau Giay', N'tung@fpt.com', N'92487629876')
INSERT [dbo].[Clients] ([id], [name], [company], [address], [email], [mobile]) VALUES (10, N'Khoa', N'FPT university', N'Cau Giay', N'Khoa@fpt.edu.vn', N'091228377')
INSERT [dbo].[Clients] ([id], [name], [company], [address], [email], [mobile]) VALUES (11, N'Cuong', N'cuong', N'abc', N'abc', N'0123')
SET IDENTITY_INSERT [dbo].[Clients] OFF
/****** Object:  Table [dbo].[Bill]    Script Date: 11/29/2013 15:48:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Bill]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Bill](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[orderid] [int] NOT NULL,
	[elevatorid] [int] NOT NULL,
	[quantity] [int] NOT NULL,
 CONSTRAINT [PK_Bill] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET IDENTITY_INSERT [dbo].[Bill] ON
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (97, 21, 1004, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (98, 21, 1005, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (99, 21, 1006, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (100, 21, 1007, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (101, 21, 1008, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (102, 22, 1006, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (103, 23, 1031, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (104, 23, 1030, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (105, 23, 1029, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (106, 23, 1028, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (107, 23, 1026, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (108, 23, 1025, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (109, 23, 1024, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (110, 23, 1023, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (111, 24, 1024, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (112, 24, 1023, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (113, 24, 1022, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (114, 24, 1021, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (115, 24, 1020, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (116, 24, 1029, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (117, 24, 1028, 1)
INSERT [dbo].[Bill] ([id], [orderid], [elevatorid], [quantity]) VALUES (118, 24, 1026, 1)
SET IDENTITY_INSERT [dbo].[Bill] OFF
/****** Object:  Table [dbo].[Users]    Script Date: 11/29/2013 15:48:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Users]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Users](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[username] [nvarchar](255) NULL,
	[password] [nvarchar](255) NULL,
	[accounttype] [int] NULL,
	[status] [int] NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET IDENTITY_INSERT [dbo].[Users] ON
INSERT [dbo].[Users] ([id], [username], [password], [accounttype], [status]) VALUES (1, N'admin', N'21232f297a57a5a743894a0e4a801fc3', 1, 1)
INSERT [dbo].[Users] ([id], [username], [password], [accounttype], [status]) VALUES (2, N'demo', N'e10adc3949ba59abbe56e057f20f883e', 2, 1)
SET IDENTITY_INSERT [dbo].[Users] OFF
/****** Object:  Table [dbo].[Order]    Script Date: 11/29/2013 15:48:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Order]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Order](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[clientid] [int] NOT NULL,
	[orderdate] [nvarchar](255) NOT NULL,
	[deliverydate] [nvarchar](255) NULL,
	[employeeid] [int] NULL,
	[price] [float] NOT NULL,
	[status] [int] NULL,
 CONSTRAINT [PK_OderDetails] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET IDENTITY_INSERT [dbo].[Order] ON
INSERT [dbo].[Order] ([id], [clientid], [orderdate], [deliverydate], [employeeid], [price], [status]) VALUES (21, 10, N'1385572203756', N'11/28/2013', 1, 66508, 3)
INSERT [dbo].[Order] ([id], [clientid], [orderdate], [deliverydate], [employeeid], [price], [status]) VALUES (22, 11, N'1385661997139', N'11/29/2013', 1, 9485, 3)
INSERT [dbo].[Order] ([id], [clientid], [orderdate], [deliverydate], [employeeid], [price], [status]) VALUES (23, 10, N'1385713039809', N'null', 1, 134199, 2)
INSERT [dbo].[Order] ([id], [clientid], [orderdate], [deliverydate], [employeeid], [price], [status]) VALUES (24, 10, N'1385713684377', N'null', 1, 109449, 0)
SET IDENTITY_INSERT [dbo].[Order] OFF
/****** Object:  Table [dbo].[Maintenance]    Script Date: 11/29/2013 15:48:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Maintenance]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Maintenance](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[billid] [int] NULL,
	[orderdate] [varchar](255) NULL,
	[deliverydate] [varchar](255) NULL,
	[firstyear] [varchar](255) NULL,
	[secondyear] [varchar](255) NULL,
	[thirdyear] [varchar](255) NULL,
 CONSTRAINT [PK_Maintenance] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
SET IDENTITY_INSERT [dbo].[Maintenance] ON
INSERT [dbo].[Maintenance] ([id], [billid], [orderdate], [deliverydate], [firstyear], [secondyear], [thirdyear]) VALUES (21, 97, N'11/28/2013', N'11/28/2013', N'11/28/2014', N'', N'')
INSERT [dbo].[Maintenance] ([id], [billid], [orderdate], [deliverydate], [firstyear], [secondyear], [thirdyear]) VALUES (22, 98, N'11/28/2013', N'11/28/2013', N'11/28/2014', N'11/28/2015', N'')
INSERT [dbo].[Maintenance] ([id], [billid], [orderdate], [deliverydate], [firstyear], [secondyear], [thirdyear]) VALUES (23, 99, N'11/28/2013', N'11/28/2013', N'11/28/2014', N'11/28/2015', N'11/28/2016')
INSERT [dbo].[Maintenance] ([id], [billid], [orderdate], [deliverydate], [firstyear], [secondyear], [thirdyear]) VALUES (24, 100, N'11/28/2013', N'11/28/2013', N'11/28/2014', N'11/28/2015', N'')
INSERT [dbo].[Maintenance] ([id], [billid], [orderdate], [deliverydate], [firstyear], [secondyear], [thirdyear]) VALUES (25, 101, N'11/28/2013', N'11/28/2013', N'11/28/2014', N'11/28/2015', N'11/28/2016')
INSERT [dbo].[Maintenance] ([id], [billid], [orderdate], [deliverydate], [firstyear], [secondyear], [thirdyear]) VALUES (26, 102, N'11/29/2013', N'11/29/2013', N'11/29/2014', N'11/29/2015', N'11/29/2016')
SET IDENTITY_INSERT [dbo].[Maintenance] OFF
/****** Object:  Table [dbo].[Employee]    Script Date: 11/29/2013 15:48:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Employee]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Employee](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[userid] [int] NULL,
	[fullname] [nvarchar](255) NULL,
	[gender] [int] NULL,
	[email] [nvarchar](255) NULL,
	[contact] [nvarchar](255) NULL,
	[active] [int] NULL,
 CONSTRAINT [PK_Employee] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]
END
GO
SET IDENTITY_INSERT [dbo].[Employee] ON
INSERT [dbo].[Employee] ([id], [userid], [fullname], [gender], [email], [contact], [active]) VALUES (1, 2, N'Le Hong Phuong', 0, N'demo@demo.com', N'0123456789', 1)
SET IDENTITY_INSERT [dbo].[Employee] OFF
/****** Object:  Table [dbo].[Complaints]    Script Date: 11/29/2013 15:48:48 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
IF NOT EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Complaints]') AND type in (N'U'))
BEGIN
CREATE TABLE [dbo].[Complaints](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[clientid] [int] NULL,
	[employeeid] [int] NULL,
	[complaint] [text] NULL,
	[resolve] [text] NULL,
	[cdate] [varchar](255) NULL,
	[rdate] [varchar](255) NULL,
	[status] [int] NULL,
 CONSTRAINT [PK_Complaints] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
END
GO
SET ANSI_PADDING OFF
GO
/****** Object:  ForeignKey [FK_OderDetails_Clients]    Script Date: 11/29/2013 15:48:48 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_OderDetails_Clients]') AND parent_object_id = OBJECT_ID(N'[dbo].[Order]'))
ALTER TABLE [dbo].[Order]  WITH CHECK ADD  CONSTRAINT [FK_OderDetails_Clients] FOREIGN KEY([clientid])
REFERENCES [dbo].[Clients] ([id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_OderDetails_Clients]') AND parent_object_id = OBJECT_ID(N'[dbo].[Order]'))
ALTER TABLE [dbo].[Order] CHECK CONSTRAINT [FK_OderDetails_Clients]
GO
/****** Object:  ForeignKey [FK_Maintenance_Bill]    Script Date: 11/29/2013 15:48:48 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Maintenance_Bill]') AND parent_object_id = OBJECT_ID(N'[dbo].[Maintenance]'))
ALTER TABLE [dbo].[Maintenance]  WITH CHECK ADD  CONSTRAINT [FK_Maintenance_Bill] FOREIGN KEY([billid])
REFERENCES [dbo].[Bill] ([id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Maintenance_Bill]') AND parent_object_id = OBJECT_ID(N'[dbo].[Maintenance]'))
ALTER TABLE [dbo].[Maintenance] CHECK CONSTRAINT [FK_Maintenance_Bill]
GO
/****** Object:  ForeignKey [FK_Employee_Users]    Script Date: 11/29/2013 15:48:48 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Employee_Users]') AND parent_object_id = OBJECT_ID(N'[dbo].[Employee]'))
ALTER TABLE [dbo].[Employee]  WITH CHECK ADD  CONSTRAINT [FK_Employee_Users] FOREIGN KEY([userid])
REFERENCES [dbo].[Users] ([id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Employee_Users]') AND parent_object_id = OBJECT_ID(N'[dbo].[Employee]'))
ALTER TABLE [dbo].[Employee] CHECK CONSTRAINT [FK_Employee_Users]
GO
/****** Object:  ForeignKey [FK_Complaints_Clients]    Script Date: 11/29/2013 15:48:48 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Complaints_Clients]') AND parent_object_id = OBJECT_ID(N'[dbo].[Complaints]'))
ALTER TABLE [dbo].[Complaints]  WITH CHECK ADD  CONSTRAINT [FK_Complaints_Clients] FOREIGN KEY([clientid])
REFERENCES [dbo].[Clients] ([id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Complaints_Clients]') AND parent_object_id = OBJECT_ID(N'[dbo].[Complaints]'))
ALTER TABLE [dbo].[Complaints] CHECK CONSTRAINT [FK_Complaints_Clients]
GO
/****** Object:  ForeignKey [FK_Complaints_Employee]    Script Date: 11/29/2013 15:48:48 ******/
IF NOT EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Complaints_Employee]') AND parent_object_id = OBJECT_ID(N'[dbo].[Complaints]'))
ALTER TABLE [dbo].[Complaints]  WITH CHECK ADD  CONSTRAINT [FK_Complaints_Employee] FOREIGN KEY([employeeid])
REFERENCES [dbo].[Employee] ([id])
GO
IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[FK_Complaints_Employee]') AND parent_object_id = OBJECT_ID(N'[dbo].[Complaints]'))
ALTER TABLE [dbo].[Complaints] CHECK CONSTRAINT [FK_Complaints_Employee]
GO
