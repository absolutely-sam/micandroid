	
		showsub: function(){
			var records = this.grid.getSelectionModel().getSelections();
			if (!JxUtil.selectone(records)) return;

			//��������
			var where_sql = 'project_src.project_id = ?';
			var where_type = 'string';
			var where_value = records[0].get('project_list__project_id');
			
			//��������
			var hdcall = function(grid) {
				//��ʾ����
				JxUtil.delay(500, function(){
					//�������ֵ
					grid.fkValue = where_value;

					Jxstar.loadData(grid, {where_sql:where_sql, where_value:where_value, where_type:where_type});
				});
			};

			//��ʾ����
			Jxstar.showData({
				filename: 'jxstar.studio.GridProjectSrc',
				title: '��Ŀ����Դ', 
				width: 700, 
				height: 600,
				callback: hdcall
			});
		},

		selproject: function(){
			var records = this.grid.getSelectionModel().getSelections();
			if (!JxUtil.selectone(records)) return;

			var keyid = records[0].get(this.define.pkcol);

			var params = 'keyid='+ keyid +'&funid='+ this.define.nodeid;
			
			params += '&pagetype=editgrid&eventcode=selproject';

			//���浱ǰ��Ŀ��Ϣ
			var hdcall = function(data) {
				Jxstar.session['project_id'] = records[0].get('project_list__project_id');
				Jxstar.session['project_code'] = records[0].get('project_list__project_code');
				Jxstar.session['project_name'] = records[0].get('project_list__project_name');
				Jxstar.session['project_path'] = records[0].get('project_list__project_path');
			};

			Request.postRequest(params, hdcall);
		}
	};