<template>
    <section>



        <!--高级查询界面-->
        <transition name="el-zoom-in-bottom">
            <el-col :span="24" class="toolbar" v-show="seniorVisible" style="padding-bottom: 0px;">

                <el-form  label-width="120px" :model="filters" ref="seniorFilters">

                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="IRCS许可证号" prop = "idcIdQuery" >
                                <el-input v-model="filters.idcIdQuery" placeholder="IRCS许可证号"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="经营者名称" prop = "idcNameQuery">
                                <el-input v-model="filters.idcNameQuery" placeholder="经营者名称"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="企业法人" prop = "corpQuery">
                                <el-input v-model="filters.corpQuery" placeholder="企业法人"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="邮编" prop = "idcZipQuery">
                                <el-input v-model="filters.idcZipQuery" placeholder="邮编"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="经营者地址" prop = "idcAddQuery">
                                <el-input v-model="filters.idcAddQuery" placeholder="经营者地址"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="安全责任人" prop = "idcOfficerQuery">
                                <el-input v-model="filters.idcOfficerQuery" placeholder="网络信息安全责任人"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="12">
                            <el-form-item label="应急联系人" prop = "emergencyContactQuery">
                                <el-input v-model="filters.emergencyContactQuery" placeholder="应急联系人"></el-input>
                            </el-form-item>
                        </el-col>

                        <el-col :span="12">
                            <el-form-item label="是否上报" prop = "ftpStatusQuery">
                                <el-select v-model="filters.ftpStatusQuery" placeholder="请选择">
                                    <el-option v-for="item in options" :label="item.label" :value="item.value" :key="item.value">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="24">
                            <el-form-item label="生成时间" prop = "timeStampRange">
                                <el-date-picker v-model="timeStampRange" type="datetimerange" placeholder="选择日期时间" :editable="false">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </el-col>
        </transition>

        <!--工具条-->
        <el-col :span="24" class="toolbar" style="padding-bottom: 0px;">

            <el-form :inline="true" :model="filters" ref="filters">
                <el-form-item label="" prop = "simpleDataQuery">
                    <el-input v-model="filters.simpleDataQuery" placeholder="IRCS许可证号模糊查询"></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" v-on:click="getIrcsList" :disabled="menuPurview.query" size="small"><i class="fa fa-search fa-lg"></i> 查询</el-button>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary" v-on:click="handleSenior" :disabled="menuPurview.query" size="small"><i :class="searchClass"></i>  高级</el-button>
                </el-form-item>

                <el-form-item>
                    <el-button type="success" v-on:click="handleReset()" :disabled="menuPurview.query" size="small"><i class="fa fa-refresh fa-lg"></i> 重置</el-button>
                </el-form-item>

            </el-form>

            <el-form :inline="true" >

                <el-form-item>
                    <el-button type="success" @click="handleAdd()" :disabled="menuPurview.add" icon="plus"  size="small">新增</el-button>
                </el-form-item>

                <el-form-item>
                    <el-button type="warning" @click="handleImport" :disabled="menuPurview.import" size="small"><i class="fa fa-cloud-upload fa-lg"></i> 导入</el-button>
                </el-form-item>

                <el-form-item>
                    <el-dropdown @command="handleExport">
                        <el-button type="primary" :disabled="menuPurview.export" size="small" ><i class="fa fa-cloud-download fa-lg"></i> 导出<i class="el-icon-caret-bottom el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="0"  :disabled = "exportDis">当页数据</el-dropdown-item>
                            <el-dropdown-item command="1"  :disabled = "exportDis">查询数据</el-dropdown-item>
                            <el-dropdown-item command="-1" :disabled = "exportDis">全部数据</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </el-form-item>
            </el-form>

        </el-col>

        <!--列表-->
        <el-table :data="ircsTest" :height="tableHigth" v-loading="listLoading" @selection-change="selsChange"
                  highlight-current-row stripe border resizable>
            <el-table-column type="selection"  >
            </el-table-column>
            <el-table-column prop="idcid"   show-overflow-tooltip label="IRCS许可证号" label-class-name="el-tooltip" sortable>
            </el-table-column>
            <el-table-column prop="idcname"    show-overflow-tooltip label="经营者名称" label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="corp"    show-overflow-tooltip label="企业法人" label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="idczip"   show-overflow-tooltip label="邮编" label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="idcadd"    show-overflow-tooltip label="经营者地址" label-class-name="el-tooltip">
            </el-table-column>

            <el-table-column label="网络信息安全责任人"  align = "center"   show-overflow-tooltip label-class-name="el-tooltip">
                <template slot-scope="scope">
                    <el-popover trigger="hover" placement="top">
                        <p>证件类型: {{ scope.row.idc_cardname }}</p>
                        <p>证件号码: {{ scope.row.idc_ID }}</p>
                        <p>固定电话: {{ scope.row.idc_tel }}</p>
                        <p>移动电话: {{ scope.row.idc_mobile }}</p>
                        <p>Email地址: {{ scope.row.idc_Email }}</p>
                        <div slot="reference" class="name-wrapper">
                            <el-tag>{{ scope.row.idc_name }}</el-tag>
                        </div>
                    </el-popover>
                </template>
            </el-table-column>

            <el-table-column label="应急联系人信息" align = "center"   show-overflow-tooltip label-class-name="el-tooltip">
                <template slot-scope="scope">
                    <el-popover trigger="hover" placement="top">
                        <p>证件类型: {{ scope.row.em_cardname }}</p>
                        <p>证件号码: {{ scope.row.em_ID }}</p>
                        <p>固定电话: {{ scope.row.em_tel }}</p>
                        <p>移动电话: {{ scope.row.em_mobile }}</p>
                        <p>Email地址: {{ scope.row.em_Email }}</p>
                        <div slot="reference" class="name-wrapper">
                            <el-tag>{{ scope.row.em_name }}</el-tag>
                        </div>
                    </el-popover>
                </template>
            </el-table-column>

            <el-table-column prop="timestamp"   show-overflow-tooltip label="生成时间" :formatter="formatTime" sortable label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="idcispnum"    show-overflow-tooltip label="机房数量" sortable label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="usernum"   show-overflow-tooltip label="用户数量" sortable label-class-name="el-tooltip">
            </el-table-column>
            <el-table-column prop="ftpstatus"    show-overflow-tooltip label="是否上报" :formatter="formatState" sortable label-class-name="el-tooltip">
            </el-table-column>

            <el-table-column type="expand" label="详情">
                <template slot-scope="props">
                    <el-form label-position="left" inline class="demo-table-expand">
                        <el-form-item label="经营者名称">
                            <span>{{ props.row.idcname }}</span>
                        </el-form-item>
                        <el-form-item label="经营者编号">
                            <span>{{ props.row.idcid }}</span>
                        </el-form-item>
                        <el-form-item label="企业法人">
                            <span>{{ props.row.corp }}</span>
                        </el-form-item>
                        <el-form-item label="邮政编码">
                            <span>{{ props.row.idczip }}</span>
                        </el-form-item>
                        <el-form-item label="经营者地址">
                            <span>{{ props.row.idcadd }}</span>
                        </el-form-item>
                        <el-form-item label="生成时间">
                            <span>{{ props.row.timestamp }}</span>
                        </el-form-item>
                        <el-form-item label="网络信息安全责任人-姓名">
                            <span>{{ props.row.idc_name }}</span>
                        </el-form-item>
                        <el-form-item label="应急联系人信息-姓名">
                            <span>{{ props.row.em_name }}</span>
                        </el-form-item>
                        <el-form-item label="网络信息安全责任人-证件类型">
                            <span>{{ props.row.idc_cardname }}</span>
                        </el-form-item>
                        <el-form-item label="应急联系人信息-证件类型">
                            <span>{{ props.row.em_cardname }}</span>
                        </el-form-item>
                        <el-form-item label="网络信息安全责任人-证件号码">
                            <span>{{ props.row.idc_ID }}</span>
                        </el-form-item>
                        <el-form-item label="应急联系人信息-证件号码">
                            <span>{{ props.row.em_ID }}</span>
                        </el-form-item>
                        <el-form-item label="网络信息安全责任人-固定电话">
                            <span>{{ props.row.idc_tel }}</span>
                        </el-form-item>
                        <el-form-item label="应急联系人信息-固定电话">
                            <span>{{ props.row.em_tel }}</span>
                        </el-form-item>
                        <el-form-item label="网络信息安全责任人-移动电话">
                            <span>{{ props.row.idc_mobile }}</span>
                        </el-form-item>
                        <el-form-item label="应急联系人信息-移动电话">
                            <span>{{ props.row.em_mobile }}</span>
                        </el-form-item>
                        <el-form-item label="网络信息安全责任人-Email地址">
                            <span>{{ props.row.idc_Email }}</span>
                        </el-form-item>
                        <el-form-item label="应急联系人信息-Email地址">
                            <span>{{ props.row.em_Email }}</span>
                        </el-form-item>
                    </el-form>
                </template>
            </el-table-column>

            <el-table-column label="操作"  width="100" header-align = "center" align = "center">
                <template slot-scope="scope">
                    <el-button-group>
                        <el-button size="small" type="primary" :disabled="menuPurview.modify" @click="handleEdit(scope.$index, scope.row)" icon="edit" title="编辑"></el-button>
                        <el-button size="small" type="danger" :disabled="menuPurview.delete" @click="handleDel(scope.$index, scope.row)" icon="delete" title="删除"></el-button>
                    </el-button-group>
                </template>
            </el-table-column>
        </el-table>

        <!--工具条-->
        <el-col :span="24" class="toolbar">
            <el-button type="danger" @click="batchRemove" :disabled="this.sels.length===0 || menuPurview.delete">批量删除</el-button>
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :page-sizes="[10,15,25,50,100]"
                    :page-size=filters.pageSize
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
                    style="float:right;">
            </el-pagination>
        </el-col>



        <!--新增界面-->
        <el-dialog title="新增" v-model="addFormVisible" :close-on-click-modal="false" size="large">
            <el-form :model="addForm" ref="addForm"  label-width="120px" :rules="addFormRules">
                <el-form-item label="IRCS许可证号" prop="idcid">
                    <el-input v-model="addForm.idcid" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="经营者名称" prop="idcname">
                    <el-input v-model="addForm.idcname" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="企业法人" prop="corp">
                    <el-input v-model="addForm.corp" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮编" prop="idczip">
                    <el-input v-model="addForm.idczip" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="经营者地址" prop="idcadd">
                    <el-input v-model="addForm.idcadd" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="是否上报">
                    <el-switch on-text="" off-text="" v-model="addForm.ftpstatus"></el-switch>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="addFormVisible = false;">取消</el-button>
                <el-button type="primary" @click.native="addSubmit" :loading="addLoading">提交</el-button>
            </div>

        </el-dialog>


        <!--编辑界面-->
        <el-dialog title="编辑" v-model="editFormVisible" :close-on-click-modal="false" size="large">
            <!-- label-width Control form description length -->
            <el-form :model="editForm" ref="editForm"  label-width="120px" :rules="editFormRules">
                <el-form-item label="IRCS许可证号" prop="idcid">
                    <el-input v-model="editForm.idcid"  auto-complete="off" :disabled="true" ></el-input>
                </el-form-item>
                <el-form-item label="经营者名称" prop="idcname">
                    <el-input v-model="editForm.idcname"  auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="企业法人" prop="corp">
                    <el-input v-model="editForm.corp"  auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="邮编" prop="idczip">
                    <el-input v-model="editForm.idczip" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="经营者地址" prop="idcadd">
                    <el-input v-model="editForm.idcadd" auto-complete="off"></el-input>
                </el-form-item>
                <el-form-item label="是否上报">
                    <el-switch on-text="" off-text="" v-model="editForm.ftpstatus"></el-switch>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click.native="editFormVisible = false">取消</el-button>
                <el-button type="primary" @click.native="editSubmit" :loading="editLoading">提交</el-button>
            </div>
        </el-dialog>

        <!--导入界面-->
        <el-dialog title="导入" v-model="importFormVisible" :close-on-click-modal="false" size="large">
            <el-upload
                    class="upload-demo"
                    action="/act-monitor-web/common/vueTest/upload.do"
                    ref="upload"
                    :headers="uploadAccept"
                    :file-list="fileList"
                    drag
                    :on-success = "importScc"
                    :on-error ="importErr"
                    :before-upload="importBefore">
                <i class="el-icon-upload"></i>
                <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                <div class="el-upload__tip" slot="tip">格式说明: xls 或xlsx 文档 (支持Excel 97-2003 工作簿 和 Excel 2007)，<a href="/act-monitor-web/common/vueTest/download.do">模板下载</a>
                    <br/><br/>
                    <el-button type="primary" @click="submitUpload">确定</el-button>
                    <el-button @click="closeImport">取消</el-button>
                </div>
            </el-upload>

        </el-dialog>


        <el-dialog title="错误信息" v-model="importInfoVisible" :close-on-click-modal="false" size="large">
            <div v-html="importInfo">
            </div>
        </el-dialog>


    </section>
</template>



<script>
    import dateUtil from '../../../js/dateUtil'
    import tableHeight from '../../../js/heightUtil'
    import purviewButton from '../../../js/purviewUtil'
    import {exportDataExcel} from '../../../js/excelUtil'
    import {getIrcsListPage, editIrcs, batchRemoveIrcs, exportIrcsList} from './test.api'
    export default {
      data () {
        return {
          tableHigth: '',
          menuPurview: {
            query: true,
            add: true,
            import: true,
            export: true,
            modify: true,
            delete: true
          },
          filters: {
            simpleDataQuery: '',
            idcIdQuery: '',
            idcNameQuery: '',
            corpQuery: '',
            idcZipQuery: '',
            idcAddQuery: '',
            idcOfficerQuery: '',
            emergencyContactQuery: '',
            timeStampQuery: '',
            ftpStatusQuery: '',
            pageIndex: 0,
            pageSize: 15,
            exportType: ''
          },
          options: [{
            value: '1',
            label: '已上报'
          }, {
            value: '0',
            label: '未上报'
          }],
          ircsTest: [],
          sels: [], // 列表选中列
          border: true,
          tooltip: true,
          listLoading: false,
          total: 0,

          // 高级查询
          seniorVisible: false,
          timeStampRange: '',

          // add
          addFormVisible: false, // 新增界面是否显示
          addLoading: false,
          // 新增界面数据
          addForm: {
            idcid: '',
            idcname: '',
            idczip: '',
            idcadd: '',
            corp: '',
            ftpstatus: false,
            idcofficer: '1',
            emergencycontact: '2',
            commonFlag: 'add'
          },
          addFormRules: {
            idcid: [
              { required: true, message: '请输入IRCS许可证号', trigger: 'blur' }
            ],
            idcname: [
              { required: true, message: '请输入经营者名称', trigger: 'blur' }
            ],
            corp: [
              { required: true, message: '请输入企业法人', trigger: 'blur' }
            ],
            idczip: [
              { required: true, message: '请输入邮编', trigger: 'blur' },
              { min: 6, max: 6, message: '邮编长度必须为6位', trigger: 'blur' }
            ],
            idcadd: [
              { required: true, message: '请输入经营者地址', trigger: 'blur' }
            ]
          },
          //                edit
          editFormVisible: false, // 编辑界面是否显示
          editLoading: false,
          editForm: {
            idcid: '',
            idcname: '',
            idczip: '',
            idcadd: '',
            corp: '',
            ftpstatus: '',
            commonFlag: 'edit'
          },
          editFormRules: {
            idcid: [
              { required: true, message: '请输入IRCS许可证号', trigger: 'blur' }
            ],
            idcname: [
              { required: true, message: '请输入经营者名称', trigger: 'blur' }
            ],
            corp: [
              { required: true, message: '请输入企业法人', trigger: 'blur' }
            ],
            idczip: [
              { required: true, message: '请输入邮编', trigger: 'blur' },
              { min: 6, max: 6, message: '邮编长度必须为6位', trigger: 'blur' }
            ],
            idcadd: [
              { required: true, message: '请输入经营者地址', trigger: 'blur' }
            ]

          },
          // 导入模块
          importFormVisible: false,
          uploadAccept: {
            accept: 'application/json'
          },
          importInfo: '',
          importInfoVisible: false,
          fileList: [],
          // 导出
          exportDis: false,
          searchClass: 'fa fa-chevron-down fa-lg'
          //   fa-chevron-up
        }
      },
      methods: {

        formatState: function (row, column) {
          return row.ftpstatus == true ? '已上报' : row.ftpstatus == false ? '未上报' : '未知'
        },
        formatTime: function (row, column) {
          row.timestamp = (!row.timestamp || row.timestamp == '') ? '' : dateUtil.formatDate.format(new Date(row.timestamp), 'yyyy-MM-dd hh:mm:ss')
          return row.timestamp
        },
        handleCurrentChange (val) {
          if (val == 1) {
            this.filters.pageIndex = 0
          } else {
            this.filters.pageIndex = (val - 1) * (this.filters.pageSize)
          }
          this.getIrcsList()
        },
        handleSizeChange (val) {
          this.filters.pageSize = val
          this.handleCurrentChange(1)
        },
        handleReset () {
          this.$refs['filters'].resetFields()
          this.$refs['seniorFilters'].resetFields()
          this.filters.pageIndex = 0,
          this.filters.pageSize = 15,
          this.timeStampRange = ''
          this.filters.timeStampQuery = ''
          this.getIrcsList()
        },
        // 高级查询
        handleSenior: function () {
          this.seniorVisible = !this.seniorVisible
          if (this.seniorVisible) {
            this.searchClass = 'fa fa-chevron-up fa-lg'
          } else {
            this.searchClass = 'fa fa-chevron-down fa-lg'
          }
        },
        // Paging query IRCS List
        getIrcsList () {
          this.listLoading = true
          this.tableHigth = tableHeight(350)
          if (this.timeStampRange[0]) {
            this.filters.timeStampQuery = [dateUtil.formatDate.format(this.timeStampRange[0], 'yyyy-MM-dd hh:mm:ss'), dateUtil.formatDate.format(this.timeStampRange[1], 'yyyy-MM-dd hh:mm:ss')]
          } else {
            this.filters.timeStampQuery = ''
          }
          getIrcsListPage(this.filters).then((data) => {
            if (data.error) {
              this.$message({
                showClose: true,
                message: data.error,
                type: 'error'
              })
            } else {
              this.total = data.success.total
              this.ircsTest = data.success.rows
            }
            this.listLoading = false
          })
    },
        selsChange: function (sels) {
          this.sels = sels
        },
        // 批量删除
        batchRemove: function () {
          var ids = this.sels.map(item => item.idcid).toString()
          this.$confirm('确认删除选中记录吗？', '提示', {
            type: 'warning'
          }).then(() => {
            let para = { deleteIds: ids }
            batchRemoveIrcs(para).then((res) => {
              if (data.error) {
                this.$message({
                  showClose: true,
                  message: data.error,
                  type: 'error'
                })
              } else {
                this.$message({
                  showClose: true,
                  message: '删除成功',
                  type: 'success'
                })
              }
              this.getIrcsList()
            })
          })
        },

        // 删除
        handleDel: function (index, row) {
          this.$confirm('确认删除该记录吗?', '提示', {
            type: 'warning'
          }).then(() => {
            let para = { deleteIds: row.idcid }
            batchRemoveIrcs(para).then((res) => {
              if (data.error) {
                this.$message({
                  message: data.error,
                  type: 'error'
                })
              } else {
                this.$message({
                  showClose: true,
                  message: '删除成功',
                  type: 'success'
                })
              }
              this.getIrcsList()
            })
          })
        },

        // 显示新增界面
        handleAdd: function () {
          if (this.$refs['addForm']) {
            this.$refs['addForm'].resetFields()
          }
          this.addFormVisible = true
        },
        // 新增
        addSubmit: function () {
          this.$refs.addForm.validate((valid) => {
            if (valid) {
              this.$confirm('确认提交吗？', '提示', {}).then(() => {
                this.addLoading = true
                editIrcs(this.addForm).then((data) => {
                  this.addLoading = false
                  if (data.error) {
                    this.$message({
                      showClose: true,
                      message: data.error,
                      type: 'error'
                    })
                  } else {
                    if (res.success == 'err') {
                      this.$message({
                        showClose: true,
                        message: '已存在此IRCS许可证号信息',
                        type: 'error'
                      })
                    } else {
                      this.$message({
                        showClose: true,
                        message: '提交成功',
                        type: 'success'
                      })
                    }
                  }
                  this.addFormVisible = false
                  this.getIrcsList()
                })
              })
            }
          })
        },
        // 显示编辑界面
        handleEdit: function (index, row) {
          this.editFormVisible = true
          this.editForm.idcid = (row.idcid)
          this.editForm.idcname = (row.idcname)
          this.editForm.idczip = (row.idczip)
          this.editForm.idcadd = (row.idcadd)
          this.editForm.ftpstatus = (row.ftpstatus)
          this.editForm.corp = (row.corp)
          this.editForm.idcofficer = (row.idcofficer)
          this.editForm.emergencycontact = (row.emergencycontact)
        },
        // 编辑
        editSubmit: function () {
          this.$refs.editForm.validate((valid) => {
            if (valid) {
              this.$confirm('确认提交吗？', '提示', {}).then(() => {
                this.editLoading = true
                editIrcs(this.editForm).then((data) => {
                  this.editLoading = false
                  if (data.error) {
                    this.$message({
                      showClose: true,
                      message: data.error,
                      type: 'error'
                    })
                  } else {
                    if (res.success == 'err') {
                      this.$message({
                        showClose: true,
                        message: '已存在此IRCS许可证号信息',
                        type: 'error'
                      })
                    } else {
                      this.$message({
                        showClose: true,
                        message: '提交成功',
                        type: 'success'
                      })
                    }
                  }
                  this.editFormVisible = false
                  this.getIrcsList()
                })
              })
            }
          })
        },
        // 导入
        handleImport: function () {
          this.importFormVisible = true
        },
        importScc: function (response, file, fileList) {
          if (response.error) {
            this.$message({
              showClose: true,
              message: response.error,
              type: 'error'
            })
          } else {
            if (response.success.uploadInfo) {
              this.importInfo = response.success.uploadInfo
              this.importInfoVisible = true
            } else {
              this.$message({
                showClose: true,
                message: '导入成功!',
                type: 'success'
              })
            }
          }
          this.fileList = []
          this.importFormVisible = false
        },
        importErr: function (err, file, fileList) {
          this.$message({
            showClose: true,
            message: '导入失败!',
            type: 'error'
          })
          this.fileList = []
          this.importFormVisible = false
        },
        importBefore: function (file) {
          const suffix = (file.name).substring((file.name).lastIndexOf('.') + 1)
          if (suffix != 'xls' && suffix != 'xlsx') {
            this.$message({
              showClose: true,
              message: '导入的文件只能为xls 或xlsx',
              type: 'error'
            })
            return false
          }
          return true
        },
        submitUpload: function () {
          this.$refs.upload.submit()
        },
        closeImport: function () {
          this.importFormVisible = false
        },
        handleExport (exportType) {
          this.filters.exportType = exportType
          this.exportDis = true
          exportDataExcel('/act-monitor-web/common/vueTest/exportExcel.do', this.filters)
          // 如果不还原导出类型 会造成导出报错
          this.filters.exportType = ''
          this.exportDis = false
        }
      },
      mounted () {
        this.tableHigth = tableHeight(350)
        this.getIrcsList()
        let secMenuPurview = JSON.parse(sessionStorage.getItem('secMenuPurview'))
        purviewButton(secMenuPurview.test, this.menuPurview)
    }
    }
</script>

<style>
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        margin-right: auto;
        margin-left: auto;
        margin-bottom: 0;
        width: 50%;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: auto;
        margin-left: auto;
        margin-bottom: 0;
        width: 50%;
    }

</style>