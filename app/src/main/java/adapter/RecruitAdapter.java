package adapter;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Date;
import java.util.List;

import bean.Offer;
import cqut.edu.cn.bangke.R;
import utils.Constants;

/**
 * Created by dhl on 2016/9/6.
 */
public class RecruitAdapter extends RecyclerView.Adapter<RecruitAdapter.MyViewHolder> {

    List<Offer> list;
    OnItemClickListener listener;
    boolean isNoMoreDatas = false;


    public RecruitAdapter(List<Offer> list) {
        this.list = list;
    }

    public OnItemClickListener getListener() {
        return listener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public void isNoMoreDatas(boolean is) {
        if (is) {
            isNoMoreDatas = is;
            this.notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = null;
        MyViewHolder holder = null;
        if (viewType == Constants.COMMON_TYPE) {
            view = inflater.inflate(R.layout.home_item, parent, false);
            holder = new MyViewHolder(view);
        } else if (viewType == Constants.FOOT_TYPE) {
            view = inflater.inflate(R.layout.loading_more, parent, false);
            holder = new MyViewHolder(view);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (position < list.size()) {
            holder.bindData(list.get(position), listener, position);
        } else {
            holder.setIsNoMoreDatas(isNoMoreDatas);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < list.size()) {
            return Constants.COMMON_TYPE;
        } else {
            return Constants.FOOT_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        //ImageView sdv_recruit_avatar;
        SimpleDraweeView sdv_recruit_avatar; //头像
        TextView tv_title;//标题 如Android开发工程师
        TextView tv_wage;//薪资
        TextView tv_comp_name;//公司名称
        TextView tv_position;//地址
        TextView tv_exp_limit;//工作经验
        TextView tv_edu_limit;//教育经历
        TextView tv_pub_time;//发布时间
        TextView tv_finance;//融资信息
        TextView tv_need;//招聘人数
        TextView tv_comp_type;//公司类型

        ProgressBar pb_loading;//进度条
        TextView tv_loading;//加载提示

        public void setIsNoMoreDatas(boolean isNoMoreDatas) {
            if (isNoMoreDatas) {
                pb_loading.setVisibility(View.GONE);
                tv_loading.setText("没有更多了~~");
            } else {
                pb_loading.setVisibility(View.VISIBLE);
                tv_loading.setText("拼命加载中...");
            }
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            sdv_recruit_avatar = (SimpleDraweeView) itemView.findViewById(R.id.sdv_recruit_avatar);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            tv_wage = (TextView) itemView.findViewById(R.id.tv_wage);
            tv_comp_name = (TextView) itemView.findViewById(R.id.tv_comp_name);
            tv_position = (TextView) itemView.findViewById(R.id.tv_position);
            tv_exp_limit = (TextView) itemView.findViewById(R.id.tv_exp_limit);
            tv_edu_limit = (TextView) itemView.findViewById(R.id.tv_edu_limit);
            tv_pub_time = (TextView) itemView.findViewById(R.id.tv_pub_time);
            tv_finance = (TextView) itemView.findViewById(R.id.tv_finance);
            tv_need = (TextView) itemView.findViewById(R.id.tv_need);
            tv_comp_type = (TextView) itemView.findViewById(R.id.tv_comp_type);

            pb_loading = (ProgressBar) itemView.findViewById(R.id.pb_load);
            tv_loading = (TextView) itemView.findViewById(R.id.tv_load);
        }

        public void bindData(Offer offer, final OnItemClickListener listener, final int position) {
            Uri path = Uri.parse(offer.getAvatar());
            sdv_recruit_avatar.setImageURI(path);
            //sdv_recruit_avatar.setImageResource(R.mipmap.ic_feedback);
            tv_title.setText(offer.getOffer_name());
            tv_wage.setText(offer.getOffer_wage());
            tv_comp_name.setText(offer.getComp_name());
            tv_position.setText(offer.getPosition());
            tv_exp_limit.setText(offer.getExp_limit());
            tv_edu_limit.setText(offer.getEdu_limit());
            Date createAt = offer.getCreatedAt();
            //有点莫名其妙
            tv_pub_time.setText((createAt.getMonth() + 1) + "月" + createAt.getDate() + "日");
            tv_finance.setText(offer.getFinance());
            tv_need.setText(offer.getNeed());
            tv_comp_type.setText(offer.getComp_type());
            //点击监听
            if (listener != null) {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.itemClick(position);
                    }
                });
            }
        }

    }

    public interface OnItemClickListener {
        void itemClick(int position);
    }
}
